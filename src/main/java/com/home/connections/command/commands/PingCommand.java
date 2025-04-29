package com.home.connections.command.commands;

import com.home.connections.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component("ping")
public class PingCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        event.getChannel().sendMessage("Pong! 🏓").queue();
    }

}
