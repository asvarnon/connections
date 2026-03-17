package com.home.connections.dao;

public interface UserDao {
    void insertUser(String discordId, String username);
    boolean userExists(String discordId);
}
