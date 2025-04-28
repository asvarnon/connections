package com.home.connections.mappers;

import com.home.connections.dto.PlayerDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PlayerMapper {

    // Get all players
    List<PlayerDto> getAllPlayers();

    // Get players filtered by archetype name
    List<PlayerDto> getPlayersByArchetype(@Param("archetypeName") String archetypeName);

}
