package com.home.connections.initializer;

import com.home.connections.listener.BotEventListener;
import com.home.connections.listener.UserJoinListener;
import jakarta.annotation.PostConstruct;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.requests.GatewayIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.stereotype.Service;

/**
 * Initializes and starts the Discord bot using JDA (Java Discord API).
 *
 * <p>This class is responsible for configuring the bot with the necessary
 * intents, setting its activity, and registering event listeners. It uses
 * Spring's {@code @Service} annotation to mark it as a managed component
 * and {@code @PostConstruct} to start the bot after the application context
 * is initialized.
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link BotEventListener} - Handles bot-related events such as message processing.</li>
 *   <li>{@link UserJoinListener} - Handles user join events in the guild.</li>
 * </ul>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Service} - Marks this class as a Spring-managed service component.</li>
 *   <li>{@code @PostConstruct} - Ensures the bot is started after dependency injection is complete.</li>
 *   <li>{@code @Value} - Injects the bot token from the application properties.</li>
 * </ul>
 */
@Service
public class DiscordBotInitializer {

    @Value("${discord.bot.token}")
    private String botToken;

    private JDA jda; // Store the JDA instance for future use if needed

    @Autowired
    private BotEventListener botEventListener;

    @Autowired
    private UserJoinListener userJoinListener;


    /**
     * Starts the Discord bot and initializes its configuration.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Creates a {@link JDABuilder} with the required gateway intents.</li>
     *   <li>Sets the bot's activity to "Spring Boot & Discord!".</li>
     *   <li>Registers the {@link BotEventListener} and {@link UserJoinListener}.</li>
     *   <li>Builds the JDA instance and waits for the bot to be ready.</li>
     * </ul>
     *
     * @throws Exception if an error occurs during bot initialization
     */
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
        builder.addEventListeners(botEventListener, userJoinListener); // Register your event listeners

        // Build and wait for the bot to be ready
        this.jda = builder.build();
        jda.awaitReady(); // Make sure the bot is fully loaded before proceeding
        System.out.println("Bot has started successfully!");
    }


}
