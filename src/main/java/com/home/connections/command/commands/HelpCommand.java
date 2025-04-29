package com.home.connections.command.commands;

import com.home.connections.command.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

@Component("help")
public class HelpCommand implements Command {

    @Override
    public void execute(MessageReceivedEvent event) {
        event.getChannel().sendMessage("""
                **InfoCommand Help**
                The `!info` command allows you to retrieve information about specific entities with optional criteria.
                
                **Usage:**
                `!info <entity> [criteria]`
                
                **Available Entities:**
                - `player`: Retrieve information about players.
                    - Examples:
                        - `!info player`: Lists all players.
                        - `!info player <name>`: Fetches a player by name.
                        - `!info player <archetype>`: Filters players by their archetype (e.g., healer, DPS).
                - `artisan`: Retrieve information about artisans.
                    - Examples:
                        - `!info artisan`: Lists all artisans.
                        - `!info artisan <artisan-type>`: Fetches artisans by their type (e.g., blacksmith, alchemist).
                
                **Notes:**
                - If an invalid entity is provided, an error message will be displayed (e.g., `❌ Unknown entity: 'example'`).
                - If no valid criteria are provided for a dynamic query, the bot displays an appropriate error message.
                
                Type `!help` for more information on additional commands.
                
            """).queue();
    }

}
