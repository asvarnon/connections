package com.home.connections.dao.impl;

import com.home.connections.dao.ItemsDao;
import com.home.connections.dto.ItemDto;
import com.home.connections.mappers.ItemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
@Repository
public class ItemsDaoImpl implements ItemsDao {

    @Autowired
    public ItemMapper itemMapper;


    @Override
    public List<ItemDto> getAllItems() {
        log.info("Fetching all items from the database");
        List<ItemDto> itemList = itemMapper.getItemList();
        log.info("Fetched {} items from the database", itemList.size());
        return itemList;
    }




}
