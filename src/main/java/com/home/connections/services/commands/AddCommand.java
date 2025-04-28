package com.home.connections.services.commands;

import com.home.connections.services.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("add")
public class AddCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        // Get the message content
        List<String> parts = List.of(event.getMessage().getContentRaw().split("\\s+"));

        if (parts.size() < 2) {
            event.getChannel().sendMessage("❌ Usage: !add <number1> <number2> ... <numberN>").queue();
            return;
        }

        try {
            int sum = parts.subList(1, parts.size()).stream()
                    .mapToInt(Integer::parseInt)
                    .sum();
            event.getChannel().sendMessage("✅ The sum is: `" + sum + "`").queue();
        } catch (NumberFormatException e) {
            event.getChannel().sendMessage("❌ All inputs must be valid numbers!").queue();
        }


    }
}
