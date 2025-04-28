package com.home.connections.services.impl;

import com.home.connections.dao.PlayerDao;
import com.home.connections.dto.PlayerDto;
import com.home.connections.services.PlayerService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerDao playerDao;

    public PlayerServiceImpl(PlayerDao playerDao) {
        this.playerDao = playerDao;
    }

    @Override
    public List<PlayerDto> getAllPlayers() {
        return playerDao.getAllPlayers();
    }

    @Override
    public List<PlayerDto> getPlayersByArchetype(String archetypeName) {
        return playerDao.getPlayersByArchetype(archetypeName);
    }


}
