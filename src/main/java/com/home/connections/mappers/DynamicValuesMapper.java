package com.home.connections.mappers;

import com.home.connections.dto.DynamicValue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface DynamicValuesMapper {

    DynamicValue getDynamicValue(@Param("category") String category, @Param("value") String value);

}
