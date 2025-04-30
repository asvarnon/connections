package com.home.connections.listener;
import com.home.connections.services.MessageService;
import com.home.connections.services.UserService;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserJoinListener extends ListenerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    @Override
    public void onGuildMemberJoin(@NotNull GuildMemberJoinEvent event) {
        String discordId = event.getUser().getId();
        String username = event.getUser().getName();

        // Delegate database handling to UserService
        userService.registerUser(discordId, username);

        // Delegate message sending to MessageService
        messageService.sendWelcomeMessage(event.getGuild(), username);
    }
}
