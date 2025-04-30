package com.home.connections.listener;

import com.home.connections.command.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Listener class that handles bot-related events, such as processing commands
 * from user messages in a guild.
 *
 * <p>This class extends {@link ListenerAdapter} from JDA and overrides the
 * {@code onMessageReceived} method to handle the {@link MessageReceivedEvent}.
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link CommandManager} - Manages and processes commands received in messages.</li>
 * </ul>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Component} - Marks this class as a Spring-managed component.</li>
 *   <li>{@code @Autowired} - Injects the required {@link CommandManager} dependency.</li>
 * </ul>
 */
@Component
public class BotEventListener extends ListenerAdapter {

    private final CommandManager commandManager;

    /**
     * Constructs a new {@code BotEventListener} with the specified {@link CommandManager}.
     *
     * @param commandManager the command manager used to process commands
     */
    @Autowired
    public BotEventListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }


    /**
     * Handles the {@link MessageReceivedEvent} when a message is received in a guild.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Ignores messages from bots.</li>
     *   <li>Checks if the message starts with the command prefix ("!").</li>
     *   <li>Extracts the command name and routes it to the {@link CommandManager}.</li>
     * </ul>
     *
     * @param event the event triggered when a message is received
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;  // Ignore messages from other bots
        }
        String message = event.getMessage().getContentRaw();
        String prefix = "!";

        // Check if the message starts with the prefix and handle commands
        if (message.startsWith(prefix)) {
            String commandName = message.substring(prefix.length()).split(" ")[0]; // Extract the command
            commandManager.processCommand(commandName, event); // Route the command
        }


    }

}
