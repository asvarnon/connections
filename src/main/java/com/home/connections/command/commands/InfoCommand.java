package com.home.connections.command.commands;

import com.home.connections.command.Command;
import com.home.connections.command.SubCommand;
import com.home.connections.command.handlers.ArtisanInfoHandler;
import com.home.connections.command.handlers.PlayerInfoHandler;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component("info")
public class InfoCommand implements Command {

    private final Map<String, SubCommand> subCommandRegistry = new HashMap<>();

    @Autowired
    private PlayerInfoHandler playerInfoHandler;

    @Autowired
    private ArtisanInfoHandler artisanInfoHandler;

    public InfoCommand() {
        registerSubCommands(); // Register all subcommands when the command is initialized
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        List<String> parts = List.of(event.getMessage().getContentRaw().split("\\s+"));

        if (parts.size() < 2) {
            event.getChannel().sendMessage("❌ Usage: !info <entity> [subcommand] [filters]").queue();
            return;
        }

        String subCommandName = String.join(" ", parts.subList(1, Math.min(3, parts.size()))).toLowerCase();
        List<String> filters = parts.subList(Math.min(3, parts.size()), parts.size());

        SubCommand subCommand = subCommandRegistry.get(subCommandName);

        if (subCommand != null) {
            subCommand.execute(filters, event);
        } else {
            event.getChannel().sendMessage("❌ Unknown subcommand: `" + subCommandName + "`.\nType `!help` for supported commands.").queue();
        }
    }

    private void registerSubCommands() {
        subCommandRegistry.put("player", (args, event) -> playerInfoHandler.handleListAll(event));
        subCommandRegistry.put("player full", (args, event) -> playerInfoHandler.handleWithArtisans(event));
        subCommandRegistry.put("player name", (args, event) -> playerInfoHandler.handleByName(String.join(" ", args), event));
        subCommandRegistry.put("player archetype", (args, event) -> playerInfoHandler.handleByArchetype(String.join(" ", args), event));

        subCommandRegistry.put("artisan", (args, event) -> artisanInfoHandler.handleDynamicQuery(args, event));
        subCommandRegistry.put("artisan type", (args, event) -> {
            if (args.isEmpty()) {
                event.getChannel().sendMessage("❌ Please provide an artisan type.").queue();
            } else {
                artisanInfoHandler.handleByType(args.get(0), event);
            }
        });
    }
}

