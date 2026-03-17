package com.home.connections.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserMapper {
    void insertUser(@Param("discordId") String discordId, @Param("username") String username);
    boolean userExists(@Param("discordId") String discordId);

}
