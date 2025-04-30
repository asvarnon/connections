package com.home.connections.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Manages the execution of commands received in messages.
 *
 * <p>This class is responsible for mapping command names to their respective
 * {@link Command} implementations and executing the appropriate command logic
 * when a command is received.
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link Command} - Represents individual command logic.</li>
 *   <li>{@link MessageReceivedEvent} - Represents the event triggered when a message is received.</li>
 * </ul>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Service} - Marks this class as a Spring-managed service component.</li>
 *   <li>{@code @Autowired} - Injects the map of command beans.</li>
 * </ul>
 */
@Service
public class CommandManager {

    private final Map<String, Command> commands;

    /**
     * Constructs a new {@code CommandManager} with the specified map of command beans.
     *
     * @param commandBeans a map of command names to their respective {@link Command} implementations
     */
    @Autowired
    public CommandManager(Map<String, Command> commandBeans) {
        this.commands = commandBeans;
    }


    /**
     * Processes a command based on the input received in a {@link MessageReceivedEvent}.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Retrieves the {@link Command} implementation associated with the command name.</li>
     *   <li>Executes the command logic if the command exists.</li>
     *   <li>Sends an error message if the command is unknown.</li>
     * </ul>
     *
     * @param commandName the name of the command to process
     * @param event the event triggered when a message is received
     */
    public void processCommand(String commandName, MessageReceivedEvent event) {
        Command command = commands.get(commandName.toLowerCase()); // Get the command by name
        if (command != null) {
            command.execute(event); // Execute the command logic
        } else {
            event.getChannel().sendMessage("❌ Unknown command. Type `!help` for a list of commands.").queue();
        }
    }
}