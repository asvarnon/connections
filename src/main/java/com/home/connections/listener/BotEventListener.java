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
        String prefix = "!";

        // Respond to specific commands
        if (message.startsWith(prefix)) {
            String command = message.substring(prefix.length()).toLowerCase();

            switch (command) {
                case "ping" -> {
                    event.getChannel().sendMessage("Pong!").queue();
                }
                case "help" -> {
                    event.getChannel().sendMessage("""
                        **Available Commands**:
                        `!ping` - Responds with Pong!
                        `!help` - Lists available commands.
                        """).queue();
                }
                default -> {
                    event.getChannel()
                            .sendMessage("Unknown command. Type `!help` for the list of commands.")
                            .queue();
                }
            }
        }

    }

}
