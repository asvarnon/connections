package com.home.connections.services.commands;

import com.home.connections.services.Command;
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