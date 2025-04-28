package com.home.connections.listener;

import com.home.connections.command.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BotEventListener extends ListenerAdapter {

    private final CommandManager commandManager;

    @Autowired
    public BotEventListener(CommandManager commandManager) {
        this.commandManager = commandManager;
    }

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
