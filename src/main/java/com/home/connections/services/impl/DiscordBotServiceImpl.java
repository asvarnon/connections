package com.home.connections.services.impl;

import com.home.connections.listener.BotEventListener;
import com.home.connections.services.DiscordBotService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.stereotype.Service;

@Service
public abstract class DiscordBotServiceImpl implements DiscordBotService {

    @Value("${discord.bot.token}")
    private String botToken;

    @PostConstruct
    public void startBot() throws Exception {
        JDABuilder builder = JDABuilder.createDefault(botToken);
        builder.setActivity(Activity.playing("Spring Boot & Discord!"));
        builder.addEventListeners(new BotEventListener()); // Register your event listeners
        builder.build();
    }

}
