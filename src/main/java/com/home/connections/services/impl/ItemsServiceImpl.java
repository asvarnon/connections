package com.home.connections.services.impl;

import com.home.connections.dao.ItemsDao;
import com.home.connections.dto.ItemDto;
import com.home.connections.services.ItemsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ItemsServiceImpl implements ItemsService {

    @Autowired
    public ItemsDao itemsDao;

    @Override
    public List<ItemDto> getAllItems() {
        log.info("Fetching all items");
        return itemsDao.getAllItems();
    }

}
