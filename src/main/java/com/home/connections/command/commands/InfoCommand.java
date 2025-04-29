package com.home.connections.command.commands;

import com.home.connections.command.Command;
import com.home.connections.command.SubCommand;
import com.home.connections.command.handlers.ArtisanInfoHandler;
import com.home.connections.command.handlers.PlayerInfoHandler;
import com.home.connections.dto.DynamicValue;
import com.home.connections.services.DynamicValueService;
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

    @Autowired
    private DynamicValueService dynamicValueService;

    public InfoCommand() {
        registerStaticSubCommands();
    }

    @Override
    public void execute(MessageReceivedEvent event) {
        List<String> parts = List.of(event.getMessage().getContentRaw().split("\\s+"));

        if (parts.size() < 2) {
            event.getChannel().sendMessage("❌ Usage: !info <entity> [criteria]").queue();
            return;
        }

        String entity = parts.get(1).toLowerCase();
        String criteria = parts.size() > 2 ? String.join(" ", parts.subList(2, parts.size())) : "";

        // Process subcommand or dynamic handling
        processCommandOrFallback(entity, criteria, event);
    }

    private void registerStaticSubCommands() {
        subCommandRegistry.put("player", (args, event) -> playerInfoHandler.handleListAll(event));
        subCommandRegistry.put("artisan", (args, event) -> artisanInfoHandler.handleListAll(event));
    }

    private void processCommandOrFallback(String entity, String criteria, MessageReceivedEvent event) {
        if (subCommandExists(entity, criteria, event)) return;
        handleDynamicCriteria(entity, criteria, event);
    }

    private boolean subCommandExists(String entity, String criteria, MessageReceivedEvent event) {
        SubCommand subCommand = subCommandRegistry.get(entity);

        if (subCommand != null && criteria.isEmpty()) {
            subCommand.execute(List.of(criteria), event); // Default list-all with no criteria
            return true;
        }
        return false;
    }

    private void handleDynamicCriteria(String entity, String criteria, MessageReceivedEvent event) {
        if (criteria.isEmpty()) {
            event.getChannel().sendMessage("❌ Please specify a value for `" + entity + "`.").queue();
            return;
        }

        DynamicValue dynamicValue = dynamicValueService.getDynamicValue(entity, criteria);

        if (dynamicValue != null) {
            processDynamicValue(entity, dynamicValue, event);
        } else {
            handleFallback(entity, criteria, event);
        }
    }

    private void processDynamicValue(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        switch (dynamicValue.getType()) {
            case "archetype" -> handleArchetype(entity, dynamicValue, event);
            case "name" -> handleName(entity, dynamicValue, event);
            case "artisan-type" -> handleArtisanType(entity, dynamicValue, event);
            default -> event.getChannel().sendMessage("❌ Unsupported type for `" + dynamicValue.getValue() + "` in `" + entity + "`.").queue();
        }
    }

    private void handleArchetype(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        if (entity.equals("player")) {
            playerInfoHandler.handleByArchetype(dynamicValue.getValue(), event);
        } else {
            event.getChannel().sendMessage("❌ Invalid archetype lookup for `" + entity + "`.").queue();
        }
    }

    private void handleName(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        if (entity.equals("player")) {
            playerInfoHandler.handleByName(dynamicValue.getValue(), event);
        } else {
            event.getChannel().sendMessage("❌ Invalid name lookup for `" + entity + "`.").queue();
        }
    }

    private void handleArtisanType(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        if ("artisan".equals(entity)) {
            artisanInfoHandler.handleByArtisanName(dynamicValue.getValue(), event);
        } else {
            event.getChannel().sendMessage("❌ Invalid artisan-type lookup for `" + entity + "`.").queue();
        }
    }


    private void handleFallback(String entity, String criteria, MessageReceivedEvent event) {
        if (entity.equals("player")) {
            playerInfoHandler.handleByName(criteria, event); // Assume name if not dynamic value
        } else if (entity.equals("artisan")) {
            event.getChannel().sendMessage("❌ Invalid artisan lookup for `" + criteria + "`. Check input.").queue();
        } else {
            event.getChannel().sendMessage("❌ Unknown entity: `" + entity + "`.").queue();
        }
    }
}