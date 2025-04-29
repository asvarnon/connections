package com.home.connections.dao;

import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.PlayerDto;

import java.util.List;

public interface ArtisanDao {

    List<ArtisanDto> getAllArtisans();

    List<ArtisanDto> getArtisansByType(String artisanType);

    List<PlayerDto> getPlayersByArtisan(String artisanName);

}
