package com.home.connections.controller;

import com.home.connections.dto.ItemDto;
import com.home.connections.services.ItemsService;
import com.home.connections.services.impl.ItemsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/items")
@Slf4j
public class ItemController {


    @Autowired
     private ItemsService itemsService;

     @GetMapping("/all")
        public List<ItemDto> getAllItems() {
            log.info("Fetching all items");
            return itemsService.getAllItems();
        }


}
