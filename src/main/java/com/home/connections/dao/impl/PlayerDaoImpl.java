package com.home.connections.dao.impl;

import com.home.connections.dao.PlayerDao;
import com.home.connections.dto.PlayerDto;
import com.home.connections.mappers.PlayerMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PlayerDaoImpl implements PlayerDao {
    private final PlayerMapper playerMapper;

    public PlayerDaoImpl(PlayerMapper playerMapper) {
        this.playerMapper = playerMapper;
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        return playerMapper.getAllPlayers();
    }

    @Override
    public List<PlayerDto> getPlayersByArchetype(String archetypeName) {
        return playerMapper.getPlayersByArchetype(archetypeName);
    }

}
