package com.home.connections.services;

import net.dv8tion.jda.api.entities.Guild;

public interface MessageService {

    public void sendWelcomeMessage(Guild guild, String username);
}
