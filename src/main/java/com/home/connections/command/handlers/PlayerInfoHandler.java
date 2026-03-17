package com.home.connections.command.handlers;

import com.home.connections.dto.PlayerDto;
import com.home.connections.services.PlayerService;
import com.home.connections.constants.DiscordConstants;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class PlayerInfoHandler {

    private final PlayerService playerService;

    public PlayerInfoHandler(PlayerService playerService) {
        this.playerService = playerService;
    }

    public void handleListAll(MessageReceivedEvent event) {
        List<PlayerDto> players = playerService.getAllPlayers();
        event.getChannel().sendMessage(formatPlayerList(players, null)).queue();
    }

    public void handleByArchetype(String archetype, MessageReceivedEvent event) {
        List<PlayerDto> players = playerService.getPlayersByArchetype(archetype);
        event.getChannel().sendMessage(formatPlayerList(players, archetype)).queue();
    }

    public void handleWithArtisans(MessageReceivedEvent event) {
        List<PlayerDto> players = playerService.getPlayersFull().stream()
                .filter(player -> player.getArtisans() != null && !player.getArtisans().isEmpty())
                .collect(Collectors.toList());
        if (players.isEmpty()) {
            event.getChannel().sendMessage("No players associated with artisans!").queue();
            return;
        }
        event.getChannel().sendMessage(formatPlayerWithArtisans(players)).queue();
    }

    public void handleByName(String playerName, MessageReceivedEvent event) {
        PlayerDto player = playerService.getPlayerByName(playerName);
        if (player == null) {
            event.getChannel().sendMessage("❌ Player `" + playerName + "` not found.").queue();
            return;
        }
        StringBuilder response = new StringBuilder("**Player Details:**\n")
                .append("- Name: ").append(player.getPlayerName()).append("\n")
                .append("- Archetype: ").append(player.getArchetypeName()).append("\n");

        if (player.getArtisans() != null && !player.getArtisans().isEmpty()) {
            response.append("**Artisans:**\n");
            player.getArtisans().forEach(artisan -> response.append("  * ")
                    .append(artisan.getArtisanName())
                    .append(" [Type: ").append(artisan.getArtisanType()).append("]\n"));
        } else {
            response.append("- No associated artisans.\n");
        }

        event.getChannel().sendMessage(response.toString()).queue();
    }

    private String formatPlayerList(List<PlayerDto> players, String archetypeFilter) {
        if (players.isEmpty()) {
            return (archetypeFilter == null ? "No players found!" :
                    "No players found with archetype `" + archetypeFilter + "`.");
        }

        StringBuilder response = new StringBuilder("✅ Players:\n");

        players.forEach(player -> response
                .append("- ").append(player.getPlayerName())
                .append(" (").append(player.getArchetypeName()).append(")\n"));

        return response.toString();
    }

    private String formatPlayerWithArtisans(List<PlayerDto> players) {
        StringBuilder response = new StringBuilder("✅ Players with Artisans:\n\n");
        players.forEach(player -> {
            response.append("- **").append(player.getPlayerName()).append("** (")
                    .append(player.getArchetypeName()).append("):\n");
            if (player.getArtisans() != null) {
                player.getArtisans().forEach(artisan -> response.append("    * ")
                        .append(artisan.getArtisanName())
                        .append(" [Type: ").append(artisan.getArtisanType()).append("]\n"));
            }
        });
        return response.toString();
    }
}