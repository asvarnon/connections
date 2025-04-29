package com.home.connections.services.impl;

import com.home.connections.dao.DynamicValueDao;
import com.home.connections.dto.DynamicValue;
import com.home.connections.mappers.DynamicValuesMapper;
import com.home.connections.services.DynamicValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicValueServiceImpl implements DynamicValueService {

    @Autowired
    public DynamicValueDao dynamicValuesDao;

    @Override
    public DynamicValue getDynamicValue(String category, String value){
        return dynamicValuesDao.getDynamicValue(category, value);
    };
}
