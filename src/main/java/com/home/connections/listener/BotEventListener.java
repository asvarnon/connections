package com.home.connections.listener;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.springframework.stereotype.Component;

@Component
public class BotEventListener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;  // Ignore messages from other bots
        }

        String message = event.getMessage().getContentRaw();

        // Simple command handling
        if (message.equalsIgnoreCase("!hello")) {
            event.getChannel().sendMessage("Hello, " + event.getAuthor().getName() + "!").queue();
        }
    }

}
