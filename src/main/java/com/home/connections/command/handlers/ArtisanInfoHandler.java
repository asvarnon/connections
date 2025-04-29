package com.home.connections.command.handlers;

import com.home.connections.dto.ArtisanDto;
import com.home.connections.services.ArtisanService;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtisanInfoHandler {

    private final ArtisanService artisanService;

    public ArtisanInfoHandler(ArtisanService artisanService) {
        this.artisanService = artisanService;
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
}