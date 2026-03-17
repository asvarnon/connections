package com.home.connections.dao;

import com.home.connections.dto.DynamicValue;
import org.apache.ibatis.annotations.Param;

public interface DynamicValueDao {

    DynamicValue getDynamicValue(String category, String value);
}
