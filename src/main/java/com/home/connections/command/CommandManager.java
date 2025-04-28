package com.home.connections.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CommandManager {

    private final Map<String, Command> commands;

    @Autowired
    public CommandManager(Map<String, Command> commandBeans) {
        this.commands = commandBeans;
    }


    // Process a command based on input
    public void processCommand(String commandName, MessageReceivedEvent event) {
        Command command = commands.get(commandName.toLowerCase()); // Get the command by name
        if (command != null) {
            command.execute(event); // Execute the command logic
        } else {
            event.getChannel().sendMessage("❌ Unknown command. Type `!help` for a list of commands.").queue();
        }
    }
}