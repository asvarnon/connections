package com.home.connections.dao.impl;

import com.home.connections.dao.ArtisanDao;
import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.PlayerDto;
import com.home.connections.mappers.ArtisanMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ArtisanDaoImpl implements ArtisanDao {

    private final ArtisanMapper artisanMapper;

    public ArtisanDaoImpl(ArtisanMapper artisanMapper) {
        this.artisanMapper = artisanMapper;
    }

    @Override
    public List<ArtisanDto> getAllArtisans() {
        return artisanMapper.getAllArtisans();
    }

    @Override
    public List<ArtisanDto> getArtisansByType(String artisanType) {
        return artisanMapper.getArtisansByType(artisanType);
    }

    @Override
    public List<PlayerDto> getPlayersByArtisan(String artisanName) {
        return artisanMapper.getPlayersByArtisan(artisanName);
    }

}
