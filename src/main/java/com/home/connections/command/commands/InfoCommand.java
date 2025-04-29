package com.home.connections.command.commands;

import com.home.connections.command.Command;
import com.home.connections.command.SubCommand;
import com.home.connections.constants.DiscordConstants;
import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.PlayerDto;
import com.home.connections.services.ArtisanService;
import com.home.connections.services.PlayerService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@Component("info")
public class InfoCommand implements Command {

    private final Map<String, SubCommand> subCommandRegistry = new HashMap<>();

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ArtisanService artisanService;

    public InfoCommand() {
        registerSubCommands(); // Register all subcommands when the command is initialized
    }



    @Override
    public void execute(MessageReceivedEvent event) {
        // Parse command parts
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
        subCommandRegistry.put("player", this::handleSpecificPlayerInfo); // Generic player handler
        subCommandRegistry.put("player full", (args, event) -> handlePlayerFullInfo(event));
        subCommandRegistry.put("player artisans", this::handlePlayerArtisans);

        // New artisan commands
        subCommandRegistry.put("artisan", this::handleSpecificArtisanInfo); // Generic artisan handler
        subCommandRegistry.put("artisan type", this::handleArtisanByType); // Handles specific "type" filters

    }

    private void handleSpecificArtisanInfo(List<String> args, MessageReceivedEvent event) {
        if (args.isEmpty()) {
            // Default behavior: List all artisans
            sendArtisanList(artisanService.getAllArtisans(), null, event);
            return;
        }

        // Join arguments to form the artisan type (e.g., "carpentry" or "metal smithing")
        String artisanType = String.join(" ", args).trim();

        // Query the database for artisans of the given type
        List<ArtisanDto> artisans = artisanService.getArtisansByType(artisanType);

        // Send the results (or a fallback message if no artisans are found)
        sendArtisanList(artisans, artisanType, event);
    }

    private void handleArtisanByType(List<String> args, MessageReceivedEvent event) {
        if (args.size() != 1) {
            // User didn't specify a valid artisan type
            event.getChannel().sendMessage("❌ Please provide an artisan type: `!info artisan type {type}`").queue();
            return;
        }

        // Extract the artisan type (e.g., "gathering")
        String artisanType = args.get(0).trim();

        // Query artisans of the specified type
        List<ArtisanDto> artisans = artisanService.getArtisansByType(artisanType);

        // Send the results or an error message
        sendArtisanList(artisans, artisanType, event);
    }


    private void handleBasicPlayerInfo(List<String> args, MessageReceivedEvent event) {
        List<PlayerDto> players = playerService.getAllPlayers();
        sendPlayerList(players, null, event);
    }

    private void handlePlayerArtisans(List<String> args, MessageReceivedEvent event) {
        List<PlayerDto> playersWithArtisans = playerService.getPlayersFull().stream()
                .filter(player -> player.getArtisans() != null && !player.getArtisans().isEmpty())
                .toList();

        if (playersWithArtisans.isEmpty()) {
            event.getChannel().sendMessage("No players are associated with artisans!").queue();
        } else {
            String response = formatPlayerWithArtisans(playersWithArtisans);
            sendSplitMessages(response, DiscordConstants.CHARACTER_LIMIT, message -> event.getChannel().sendMessage(message).queue());
        }
    }

    private void handleSpecificPlayerInfo(List<String> args, MessageReceivedEvent event) {
        if (args.isEmpty()) {
            event.getChannel().sendMessage("❌ Please provide a player name: `!info player {player_name}`").queue();
            return;
        }

        // Join args to form the full player name (in case of multi-word names)
        String playerName = String.join(" ", args).trim();

        // Query the player by name
        PlayerDto player = playerService.getPlayerByName(playerName);

        if (player == null) {
            event.getChannel().sendMessage("❌ Player `" + playerName + "` not found.").queue();
        } else {
            StringBuilder response = new StringBuilder();

            // Add player details
            response.append("**Player Details:**\n")
                    .append("- Name: ").append(player.getPlayerName()).append("\n")
                    .append("- Archetype: ").append(player.getArchetypeName()).append("\n");

            // Check associated artisans
            if (player.getArtisans() != null && !player.getArtisans().isEmpty()) {
                response.append("\n**Artisans:**\n");
                for (ArtisanDto artisan : player.getArtisans()) {
                    response.append("  * ").append(artisan.getArtisanName())
                            .append(" [Type: ").append(artisan.getArtisanType()).append("]\n");
                }
            } else {
                response.append("\n- No associated artisans.\n");
            }

            // Send the response
            event.getChannel().sendMessage(response.toString()).queue();
        }
    }


    private void handleAllArtisans(MessageReceivedEvent event) {
        List<ArtisanDto> artisans = artisanService.getAllArtisans();
        sendArtisanList(artisans, null, event);
    }

    private void handleFilteredArtisanInfo(List<String> args, MessageReceivedEvent event) {
        if (args.size() == 2 && "type".equalsIgnoreCase(args.get(0))) {
            String type = args.get(1);
            List<ArtisanDto> artisans = artisanService.getArtisansByType(type);
            sendArtisanList(artisans, type, event);
        } else {
            event.getChannel().sendMessage("❌ Invalid filters for `artisan`. Usage: !info artisan [type <name>]").queue();
        }
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


    private void handlePlayerFullInfo(MessageReceivedEvent event) {
        List<PlayerDto> players = playerService.getPlayersFull();
        if (players.isEmpty()) {
            event.getChannel().sendMessage("No players with associated artisans found!").queue();
        } else {
            String fullResponse = formatPlayerWithArtisans(players);
            sendSplitMessages(fullResponse, DiscordConstants.CHARACTER_LIMIT, message -> event.getChannel().sendMessage(message).queue());
        }
    }



    private void sendSplitMessages(String content, int maxLength, Consumer<String> messageSender) {
        int start = 0;
        while (start < content.length()) {
            int end = Math.min(start + maxLength, content.length());
            messageSender.accept(content.substring(start, end)); // Delegate message-sending logic
            start += maxLength;
        }
    }




    private String formatPlayerWithArtisans(List<PlayerDto> players) {
        StringBuilder response = new StringBuilder("✅ Player Details with Artisans:\n\n");

        // Format each player's details
        players.forEach(player -> {
            response.append("- **").append(player.getPlayerName()).append("** (")
                    .append(player.getArchetypeName())
                    .append("):\n");

            if (player.getArtisans() != null && !player.getArtisans().isEmpty()) {
                player.getArtisans().forEach(artisan -> response.append("    * ")
                        .append(artisan.getArtisanName())
                        .append(" [Type: ").append(artisan.getArtisanType())
                        .append("]\n"));
            } else {
                response.append("    No associated artisans.\n");
            }

            response.append("\n");
        });

        return response.toString();
    }







}

