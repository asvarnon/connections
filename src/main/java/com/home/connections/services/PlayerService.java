package com.home.connections.services;

import com.home.connections.dto.PlayerDto;

import java.util.List;

public interface PlayerService {
    List<PlayerDto> getAllPlayers();

    List<PlayerDto> getPlayersByArchetype(String archetypeName);

    List<PlayerDto> getPlayersFull();

    PlayerDto getPlayerByName(String playerName);
}
