package com.home.connections.services.impl;

import com.home.connections.listener.BotEventListener;
import com.home.connections.services.DiscordBotService;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.stereotype.Service;

@Service
public class DiscordBotServiceImpl implements DiscordBotService {

    @Value("${discord.bot.token}")
    private String botToken;

    private JDA jda; // Store the JDA instance for future use if needed

    @Autowired
    private BotEventListener botEventListener;


    @PostConstruct
    public void startBot() throws Exception {
        // Initialize the JDA builder with required GatewayIntents
        JDABuilder builder = JDABuilder.createDefault(
                botToken,
                GatewayIntent.MESSAGE_CONTENT,   // Intent to access message content
                GatewayIntent.GUILD_MESSAGES,    // Intent to handle guild messages
                GatewayIntent.GUILD_MEMBERS      // Intent to handle member events
        );

        builder.setActivity(Activity.playing("Spring Boot & Discord!")); // Set bot's activity
        builder.addEventListeners(botEventListener); // Register your event listeners

        // Build and wait for the bot to be ready
        this.jda = builder.build();
        jda.awaitReady(); // Make sure the bot is fully loaded before proceeding
        System.out.println("Bot has started successfully!");
    }


}
