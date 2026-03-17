package com.home.connections.services;

import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.PlayerDto;

import java.util.List;

public interface ArtisanService {

    List<ArtisanDto> getAllArtisans();

    List<ArtisanDto> getArtisansByType(String artisanType);

    List<PlayerDto> getPlayersByArtisan(String artisanName);

}
