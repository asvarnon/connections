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
/**
 * Command implementation for handling the "info" command.
 *
 * <p>This command provides information about various entities such as players and artisans.
 * It supports both static subcommands and dynamic criteria-based lookups.</p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link PlayerInfoHandler} - Handles player-related information.</li>
 *   <li>{@link ArtisanInfoHandler} - Handles artisan-related information.</li>
 *   <li>{@link DynamicValueService} - Provides dynamic value lookups to parse strings for subcommands.</li>
 * </ul>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Component} - Marks this class as a Spring-managed component with the name "info".</li>
 *   <li>{@code @Autowired} - Injects the required dependencies.</li>
 * </ul>
 */
@Component("info")
public class InfoCommand implements Command {

    private final Map<String, SubCommand> subCommandRegistry = new HashMap<>();

    @Autowired
    private PlayerInfoHandler playerInfoHandler;

    @Autowired
    private ArtisanInfoHandler artisanInfoHandler;

    @Autowired
    private DynamicValueService dynamicValueService;

    /**
     * Constructs a new {@code InfoCommand} and registers static subcommands.
     */
    public InfoCommand() {
        registerStaticSubCommands();
    }

    /**
     * Executes the "info" command based on the provided message event.
     *
     * <p>This method parses the command arguments and determines whether to
     * execute a static subcommand or perform a dynamic lookup.</p>
     *
     * @param event the event triggered when a message is received
     */
    @Override
    public void execute(MessageReceivedEvent event) {
        List<String> parts = List.of(event.getMessage().getContentRaw().split("\\s+"));

        if (parts.size() < 2) {
            event.getChannel().sendMessage("❌ Usage: !info <entity> [criteria]").queue();
            return;
        }

        String entity = parts.get(1).toLowerCase();
        String criteria = parts.size() > 2 ? String.join(" ", parts.subList(2, parts.size())) : "";

        processCommandOrFallback(entity, criteria, event);
    }

    /**
     * Registers static subcommands for the "info" command.
     */
    private void registerStaticSubCommands() {
        subCommandRegistry.put("player", (args, event) -> playerInfoHandler.handleListAll(event));
        subCommandRegistry.put("artisan", (args, event) -> artisanInfoHandler.handleListAll(event));
    }

    /**
     * Processes a subcommand or falls back to dynamic criteria handling.
     *
     * @param entity the entity type (e.g., "player", "artisan")
     * @param criteria the criteria for the lookup
     * @param event the event triggered when a message is received
     */
    private void processCommandOrFallback(String entity, String criteria, MessageReceivedEvent event) {
        if (subCommandExists(entity, criteria, event)) return;
        handleDynamicCriteria(entity, criteria, event);
    }

    /**
     * Checks if a static subcommand exists and executes it if applicable.
     *
     * @param entity the entity type
     * @param criteria the criteria for the lookup
     * @param event the event triggered when a message is received
     * @return {@code true} if a subcommand was executed, {@code false} otherwise
     */
    private boolean subCommandExists(String entity, String criteria, MessageReceivedEvent event) {
        SubCommand subCommand = subCommandRegistry.get(entity);

        if (subCommand != null && criteria.isEmpty()) {
            subCommand.execute(List.of(criteria), event);
            return true;
        }
        return false;
    }

    /**
     * Handles dynamic criteria-based lookups for entities.
     *
     * @param entity the entity type
     * @param criteria the criteria for the lookup
     * @param event the event triggered when a message is received
     */
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

    /**
     * Processes a dynamic value and executes the appropriate handler.
     *
     * @param entity the entity type
     * @param dynamicValue the dynamic value retrieved from the service
     * @param event the event triggered when a message is received
     */
    private void processDynamicValue(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        switch (dynamicValue.getType()) {
            case "archetype" -> handleArchetype(entity, dynamicValue, event);
            case "name" -> handleName(entity, dynamicValue, event);
            case "artisan-type" -> handleArtisanType(entity, dynamicValue, event);
            default -> event.getChannel().sendMessage("❌ Unsupported type for `" + dynamicValue.getValue() + "` in `" + entity + "`.").queue();
        }
    }

    /**
     * Handles archetype-based lookups for players.
     *
     * @param entity the entity type
     * @param dynamicValue the dynamic value retrieved from the service
     * @param event the event triggered when a message is received
     */
    private void handleArchetype(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        if (entity.equals("player")) {
            playerInfoHandler.handleByArchetype(dynamicValue.getValue(), event);
        } else {
            event.getChannel().sendMessage("❌ Invalid archetype lookup for `" + entity + "`.").queue();
        }
    }

    /**
     * Handles name-based lookups for players.
     *
     * @param entity the entity type
     * @param dynamicValue the dynamic value retrieved from the service
     * @param event the event triggered when a message is received
     */
    private void handleName(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        if (entity.equals("player")) {
            playerInfoHandler.handleByName(dynamicValue.getValue(), event);
        } else {
            event.getChannel().sendMessage("❌ Invalid name lookup for `" + entity + "`.").queue();
        }
    }

    /**
     * Handles artisan-type-based lookups for players.
     *
     * @param entity the entity type
     * @param dynamicValue the dynamic value retrieved from the service
     * @param event the event triggered when a message is received
     */
    private void handleArtisanType(String entity, DynamicValue dynamicValue, MessageReceivedEvent event) {
        if ("artisan".equals(entity)) {
            artisanInfoHandler.handleByArtisanName(dynamicValue.getValue(), event);
        } else {
            event.getChannel().sendMessage("❌ Invalid artisan-type lookup for `" + entity + "`.").queue();
        }
    }

    /**
     * Handles fallback logic for unsupported or invalid lookups.
     *
     * @param entity the entity type
     * @param criteria the criteria for the lookup
     * @param event the event triggered when a message is received
     */
    private void handleFallback(String entity, String criteria, MessageReceivedEvent event) {
        if (entity.equals("player")) {
            playerInfoHandler.handleByName(criteria, event);
        } else if (entity.equals("artisan")) {
            event.getChannel().sendMessage("❌ Invalid artisan lookup for `" + criteria + "`. Check input.").queue();
        } else {
            event.getChannel().sendMessage("❌ Unknown entity: `" + entity + "`.").queue();
        }
    }
}