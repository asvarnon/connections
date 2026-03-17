package com.home.connections.command.handlers;

import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.DynamicValue;
import com.home.connections.dto.PlayerDto;
import com.home.connections.services.ArtisanService;
import com.home.connections.services.DynamicValueService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Handles artisan-related commands and queries for the Discord bot.
 *
 * <p>This class provides methods to process and respond to artisan-related
 * information requests, such as listing all artisans, filtering artisans by type,
 * and handling dynamic queries. It integrates with the {@link ArtisanService} and
 * {@link DynamicValueService} to retrieve and process data.</p>
 *
 * <p>Dependencies:
 * <ul>
 *   <li>{@link ArtisanService} - Provides access to artisan data.</li>
 *   <li>{@link DynamicValueService} - Handles dynamic value lookups for artisans.</li>
 * </ul>
 *
 * <p>Annotations:
 * <ul>
 *   <li>{@code @Component} - Marks this class as a Spring-managed component.</li>
 * </ul>
 */
@Component
public class ArtisanInfoHandler {

    private final ArtisanService artisanService;
    private final DynamicValueService dynamicValueService;

    /**
     * Constructs a new {@code ArtisanInfoHandler} with the required dependencies.
     *
     * @param artisanService the service for accessing artisan data
     * @param dynamicValueService the service for handling dynamic value lookups
     */
    public ArtisanInfoHandler(ArtisanService artisanService, DynamicValueService dynamicValueService) {
        this.artisanService = artisanService;
        this.dynamicValueService = dynamicValueService;
    }

    /**
     * Handles the command to list all artisans.
     *
     * @param event the event triggered when a message is received
     */
    public void handleListAll(MessageReceivedEvent event) {
        List<ArtisanDto> artisans = artisanService.getAllArtisans();
        event.getChannel().sendMessage(formatArtisanList(artisans, null)).queue();
    }

    /**
     * Handles the command to list artisans by type.
     *
     * @param type the type of artisans to filter
     * @param event the event triggered when a message is received
     */
    public void handleByType(String type, MessageReceivedEvent event) {
        List<ArtisanDto> artisans = artisanService.getArtisansByType(type);
        event.getChannel().sendMessage(formatArtisanList(artisans, type)).queue();
    }

    /**
     * Handles dynamic queries for artisans based on provided arguments.
     *
     * @param args the arguments for the query
     * @param event the event triggered when a message is received
     */
    public void handleDynamicQuery(List<String> args, MessageReceivedEvent event) {
        if (args.isEmpty()) {
            handleListAll(event);
            return;
        }

        String artisanType = String.join(" ", args).trim();
        handleByType(artisanType, event);
    }

    /**
     * Formats a list of artisans into a response message.
     *
     * @param artisans the list of artisans to format
     * @param typeFilter the type filter applied, or {@code null} if none
     * @return the formatted response message
     */
    private String formatArtisanList(List<ArtisanDto> artisans, String typeFilter) {
        if (artisans.isEmpty()) {
            return typeFilter == null
                    ? "No artisans found!"
                    : "No artisans found for type `" + typeFilter + "`.";
        }

        StringBuilder response = new StringBuilder();
        response.append(typeFilter == null ? "✅ Artisans:\n" : "✅ Artisans of type `" + typeFilter + "`:\n");

        artisans.forEach(artisan -> response.append("- ")
                .append(artisan.getArtisanName())
                .append(" [Type: ").append(artisan.getArtisanType()).append("]\n"));

        return response.toString();
    }

    /**
     * Handles the command to retrieve players associated with a specific artisan.
     *
     * @param artisanName the name of the artisan
     * @param event the event triggered when a message is received
     */
    public void handleByArtisanName(String artisanName, MessageReceivedEvent event) {
        DynamicValue dynamicValue = dynamicValueService.getDynamicValue("artisan", artisanName);

        if (dynamicValue == null || !"artisan-type".equals(dynamicValue.getType())) {
            event.getChannel().sendMessage("❌ Invalid artisan name: `" + artisanName + "`. Ensure it's valid and try again.").queue();
            return;
        }

        List<PlayerDto> players = artisanService.getPlayersByArtisan(dynamicValue.getValue());

        if (players.isEmpty()) {
            event.getChannel().sendMessage("❌ No players found associated with the artisan `" + artisanName + "`.").queue();
            return;
        }

        event.getChannel().sendMessage(formatPlayersWithArtisan(players, artisanName)).queue();
    }

    /**
     * Formats a list of players associated with a specific artisan into a response message.
     *
     * @param players the list of players to format
     * @param artisanName the name of the artisan
     * @return the formatted response message
     */
    private String formatPlayersWithArtisan(List<PlayerDto> players, String artisanName) {
        StringBuilder response = new StringBuilder("✅ Players with artisan **" + artisanName + "**:\n");

        players.forEach(player -> response.append("- ")
                .append(player.getPlayerName())
                .append("\n"));

        return response.toString();
    }
}