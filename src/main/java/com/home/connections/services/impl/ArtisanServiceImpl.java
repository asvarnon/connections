package com.home.connections.services.impl;

import com.home.connections.dao.ArtisanDao;
import com.home.connections.dto.ArtisanDto;
import com.home.connections.dto.PlayerDto;
import com.home.connections.services.ArtisanService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtisanServiceImpl implements ArtisanService {

    private final ArtisanDao artisanDao;

    public ArtisanServiceImpl(ArtisanDao artisanDao) {
        this.artisanDao = artisanDao;
    }

    @Override
    public List<ArtisanDto> getAllArtisans() {
        return artisanDao.getAllArtisans();
    }

    @Override
    public List<ArtisanDto> getArtisansByType(String artisanType) {
        return artisanDao.getArtisansByType(artisanType);
    }

    @Override
    public List<PlayerDto> getPlayersByArtisan(String artisanName) {
        return artisanDao.getPlayersByArtisan(artisanName);
    }


}
