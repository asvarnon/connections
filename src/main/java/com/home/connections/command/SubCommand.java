package com.home.connections.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

@FunctionalInterface
public interface SubCommand {
    void execute(List<String> args, MessageReceivedEvent event);
}

