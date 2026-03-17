package com.home.connections.listener;
import com.home.connections.services.MessageService;
import com.home.connections.services.UserService;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Listener class that handles the event when a new member joins a guild.
 * It registers the user in the database and sends a welcome message.
 *
 */
@Component
public class UserJoinListener extends ListenerAdapter {

    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;


    /**
     * Handles the {@link GuildMemberJoinEvent} when a new member joins a guild.
     *
     * <p>This method performs the following actions:
     * <ul>
     *   <li>Registers the user in the database using {@link UserService#registerUser}.</li>
     *   <li>Sends a welcome message using {@link MessageService#sendWelcomeMessage}.</li>
     * </ul>
     *
     * @param event the event triggered when a new member joins a guild
     */
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
