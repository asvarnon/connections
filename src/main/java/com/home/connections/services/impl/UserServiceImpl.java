package com.home.connections.services.impl;

import com.home.connections.dao.UserDao;
import com.home.connections.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    public void registerUser(String discordId, String username) {
        if (!userDao.userExists(discordId)) {
            userDao.insertUser(discordId, username);
        }
    }

}
