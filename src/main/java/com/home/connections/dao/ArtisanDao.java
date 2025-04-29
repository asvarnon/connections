package com.home.connections.dao;

import com.home.connections.dto.ArtisanDto;

import java.util.List;

public interface ArtisanDao {

    List<ArtisanDto> getAllArtisans();

    List<ArtisanDto> getArtisansByType(String artisanType);


}
