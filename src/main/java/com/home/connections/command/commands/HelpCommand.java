package com.home.connections.command.commands;

import com.home.connections.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component("help")
public class HelpCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        event.getChannel().sendMessage("""
            **Available Commands**
            `!ping` - Check bot responsiveness.
            `!help` - Show the help menu.
            `!hello` - Greet the bot.
            """).queue();
    }

}
