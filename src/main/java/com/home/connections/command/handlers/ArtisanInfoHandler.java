package com.home.connections.command.handlers;

import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.DynamicValue;
import com.home.connections.dto.PlayerDto;
import com.home.connections.services.ArtisanService;
import com.home.connections.services.DynamicValueService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtisanInfoHandler {

    private final ArtisanService artisanService;
    private final DynamicValueService dynamicValueService;

    public ArtisanInfoHandler(ArtisanService artisanService, DynamicValueService dynamicValueService) {
        this.artisanService = artisanService;
        this.dynamicValueService = dynamicValueService;
    }

    public void handleListAll(MessageReceivedEvent event) {
        List<ArtisanDto> artisans = artisanService.getAllArtisans();
        event.getChannel().sendMessage(formatArtisanList(artisans, null)).queue();
    }

    public void handleByType(String type, MessageReceivedEvent event) {
        List<ArtisanDto> artisans = artisanService.getArtisansByType(type);
        event.getChannel().sendMessage(formatArtisanList(artisans, type)).queue();
    }

    public void handleDynamicQuery(List<String> args, MessageReceivedEvent event) {
        if (args.isEmpty()) {
            handleListAll(event);
            return;
        }

        String artisanType = String.join(" ", args).trim();
        handleByType(artisanType, event);
    }

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

    public void handleByArtisanName(String artisanName, MessageReceivedEvent event) {
        // Validate the artisan name using the DYNAMIC_VALUES table
        DynamicValue dynamicValue = dynamicValueService.getDynamicValue("artisan", artisanName);

        if (dynamicValue == null || !"artisan-type".equals(dynamicValue.getType())) {
            event.getChannel().sendMessage("❌ Invalid artisan name: `" + artisanName + "`. Ensure it's valid and try again.").queue();
            return;
        }

        // Retrieve the artisan ID by name
        List<PlayerDto> players = artisanService.getPlayersByArtisan(dynamicValue.getValue());

        if (players.isEmpty()) {
            event.getChannel().sendMessage("❌ No players found associated with the artisan `" + artisanName + "`.").queue();
            return;
        }

        // Format and send the result
        event.getChannel().sendMessage(formatPlayersWithArtisan(players, artisanName)).queue();
    }

    private String formatPlayersWithArtisan(List<PlayerDto> players, String artisanName) {
        StringBuilder response = new StringBuilder("✅ Players with artisan **" + artisanName + "**:\n");

        players.forEach(player -> response.append("- ")
                .append(player.getPlayerName())
                .append("\n"));

        return response.toString();
    }

}