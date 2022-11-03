package com.example.booking.service;

import com.example.booking.connection.ConnectionPoolImpl;
import com.example.booking.connection.WrapperConnection;
import com.example.booking.dao.BaseUserDao;
import com.example.booking.dao.UserDaoImpl;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final Logger LOGGER = Logger.getLogger(UserService.class);
    private static UserService instance;

    public static UserService getInstance() {
        if (instance == null) {
            synchronized (UserService.class) {
                if (instance == null) {
                    instance = new UserService();
                }
            }
        }
        return instance;
    }

    private UserService() {
    }

    public static List<User> getAll() {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseUserDao userDao = new UserDaoImpl(connection);
            return userDao.getAll();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
