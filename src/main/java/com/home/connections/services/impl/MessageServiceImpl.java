package com.home.connections.services.impl;

import com.home.connections.services.MessageService;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    public void sendWelcomeMessage(Guild guild, String username) {
        if (guild.getDefaultChannel() instanceof TextChannel textChannel) {
            textChannel.sendMessage("Welcome to the server, " + username + "!").queue();
        }
    }
}
