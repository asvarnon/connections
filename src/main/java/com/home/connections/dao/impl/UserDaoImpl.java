package com.home.connections.dao.impl;

import com.home.connections.dao.UserDao;
import com.home.connections.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private UserMapper userMapper;

    public boolean userExists(String discordId) {
        return userMapper.userExists(discordId);
    }

    public void insertUser(String discordId, String username) {
        userMapper.insertUser(discordId, username);
    }

}
