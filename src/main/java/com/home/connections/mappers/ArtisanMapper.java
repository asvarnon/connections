package com.home.connections.mappers;

import com.home.connections.dto.ArtisanDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ArtisanMapper {

    // Retrieve all artisans
    List<ArtisanDto> getAllArtisans();

    // Retrieve artisans by their type (e.g., Gathering, Processing, Crafting)
    List<ArtisanDto> getArtisansByType(@Param("artisanType") String artisanType);


}
