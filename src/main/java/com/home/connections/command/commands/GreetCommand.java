package com.home.connections.command.commands;

import com.home.connections.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component("hello")
public class GreetCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        String user = event.getAuthor().getName();
        event.getChannel().sendMessage("Hello, " + user + "! 👋").queue();
    }
}