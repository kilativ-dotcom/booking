package com.example.booking.dao;

import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;

import java.util.Optional;

public interface BaseUserDao extends BaseDao<User> {
    Optional<User> getByLoginPassword(String login, String password) throws DaoException;

    Optional<User> addUser(String login, String password) throws DaoException;
}
