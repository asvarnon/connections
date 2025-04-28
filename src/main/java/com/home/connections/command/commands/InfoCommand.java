package com.home.connections.command.commands;

import com.home.connections.command.Command;
import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.PlayerDto;
import com.home.connections.services.ArtisanService;
import com.home.connections.services.PlayerService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("info")
public class InfoCommand implements Command {

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ArtisanService artisanService;

//    public InfoCommand(PlayerService playerService, ArtisanService artisanService) {
//        this.playerService = playerService;
//        this.artisanService = artisanService;
//    }

    @Override
    public void execute(MessageReceivedEvent event) {
        // Split the command message into parameters
        List<String> parts = List.of(event.getMessage().getContentRaw().split("\\s+"));

        if (parts.size() < 2) {
            event.getChannel().sendMessage("❌ Usage: !info <entity> [filters]").queue();
            return;
        }

        String entity = parts.get(1).toLowerCase(); // e.g., player, artisan
        List<String> filters = parts.subList(2, parts.size()); // Any additional filters

        try {
            switch (entity) {
                case "player":
                    handlePlayerInfo(filters, event);
                    break;
                case "artisan":
                    handleArtisanInfo(filters, event);
                    break;
                default:
                    event.getChannel().sendMessage("❌ Unknown entity: `" + entity + "`.\nSupported entities: `player`, `artisan`").queue();
            }
        } catch (Exception e) {
            event.getChannel().sendMessage("❌ An error occurred while processing your request: " + e.getMessage()).queue();
        }
    }


    private void handlePlayerInfo(List<String> args, MessageReceivedEvent event) {
        // Handle basic query: `!info player` (no filters)
        if (args.isEmpty()) {
            sendPlayerList(playerService.getAllPlayers(), null, event);
            return;
        }

        // Handle filtered query: `!info player archetype <name>`
        if (args.size() == 2 && "archetype".equalsIgnoreCase(args.get(0))) {
            String archetype = args.get(1);
            sendPlayerList(playerService.getPlayersByArchetype(archetype), archetype, event);
            return;
        }

        // If no valid format matches, send an error message
        event.getChannel().sendMessage("❌ Invalid command format. Usage: !info player [archetype <name>]").queue();
    }




    private void handleArtisanInfo(List<String> filters, MessageReceivedEvent event) {
        // Handle case: `!info artisan` (no filters)
        if (filters.isEmpty()) {
            sendArtisanList(artisanService.getAllArtisans(), null, event);
            return;
        }

        // Handle case: `!info artisan type <name>`
        if (filters.size() == 2 && "type".equalsIgnoreCase(filters.get(0))) {
            String type = filters.get(1);
            sendArtisanList(artisanService.getArtisansByType(type), type, event);
            return;
        }

        // Invalid filters
        event.getChannel().sendMessage("❌ Invalid filters for `artisan`. Usage: !info artisan [type <name>]").queue();
    }


    private String formatPlayerList(List<PlayerDto> players, String archetypeFilter) {
        StringBuilder response = new StringBuilder();

        // Add initial message based on the filtering condition
        if (archetypeFilter == null) {
            response.append("✅ Players:\n");
        } else {
            response.append("✅ Players with archetype `")
                    .append(archetypeFilter)
                    .append("`:\n");
        }

        // Append player details
        players.forEach(player -> response
                .append("- ")
                .append(player.getPlayerName())
                .append(" (").append(player.getArchetypeName()).append(")\n"));

        return response.toString();
    }

    private void sendPlayerList(List<PlayerDto> players, String archetypeFilter, MessageReceivedEvent event) {
        if (players.isEmpty()) {
            String message = (archetypeFilter == null)
                    ? "No players found!"
                    : "No players found for archetype `" + archetypeFilter + "`.";
            event.getChannel().sendMessage(message).queue();
        } else {
            event.getChannel().sendMessage(formatPlayerList(players, archetypeFilter)).queue();
        }
    }

    private void sendArtisanList(List<ArtisanDto> artisans, String typeFilter, MessageReceivedEvent event) {
        if (artisans.isEmpty()) {
            String message = (typeFilter == null)
                    ? "No artisans found!"
                    : "No artisans found for type `" + typeFilter + "`.";
            event.getChannel().sendMessage(message).queue();
        } else {
            event.getChannel().sendMessage(formatArtisanList(artisans, typeFilter)).queue();
        }
    }

    private String formatArtisanList(List<ArtisanDto> artisans, String typeFilter) {
        StringBuilder response = new StringBuilder();

        // Header message
        if (typeFilter == null) {
            response.append("✅ Artisans:\n");
        } else {
            response.append("✅ Artisans of type `").append(typeFilter).append("`:\n");
        }

        // Append each artisan's details
        artisans.forEach(artisan -> response
                .append("- ")
                .append(artisan.getArtisanName())
                .append(" [Type: ").append(artisan.getArtisanType()).append("]\n"));

        return response.toString();
    }





}

