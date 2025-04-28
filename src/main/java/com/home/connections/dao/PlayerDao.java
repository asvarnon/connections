package com.home.connections.dao;

import com.home.connections.dto.PlayerDto;

import java.util.List;

public interface PlayerDao {
    List<PlayerDto> getAllPlayers();

    List<PlayerDto> getPlayersByArchetype(String archetypeName);
}
