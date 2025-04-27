package com.home.connections.mappers;

import com.home.connections.dto.ItemDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemMapper {

    List<ItemDto> getItemList();

}
