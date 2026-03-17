package com.home.connections.dao.impl;

import com.home.connections.dao.DynamicValueDao;
import com.home.connections.dto.DynamicValue;
import com.home.connections.dto.PlayerDto;
import com.home.connections.mappers.DynamicValuesMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class DynamicValueDaoImpl implements DynamicValueDao {

    @Autowired
    public DynamicValuesMapper dynamicValuesMapper;

    @Override
    public DynamicValue getDynamicValue(String category, String value){
        return dynamicValuesMapper.getDynamicValue(category, value);
    };


}
