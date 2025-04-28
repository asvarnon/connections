package com.home.connections.services.commands;

import com.home.connections.services.Command;
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
