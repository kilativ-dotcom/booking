package com.example.booking.service;

import com.example.booking.connection.ConnectionPoolImpl;
import com.example.booking.connection.WrapperConnection;
import com.example.booking.dao.BaseUserDao;
import com.example.booking.dao.UserDaoImpl;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

public class LoginService {
    private static final Logger LOGGER = Logger.getLogger(LoginService.class);
    private static LoginService instance;

    public static LoginService getInstance() {
        if (instance == null) {
            synchronized (LoginService.class) {
                if (instance == null) {
                    instance = new LoginService();
                }
            }
        }
        return instance;
    }

    private LoginService() {
    }

    public Optional<User> checkLoginPassword(String login, String password) {
        LOGGER.trace("user: " + login);
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            String encodedPassword = PasswordEncoder.encode(password);
            BaseUserDao userDao = new UserDaoImpl(connection);
            return userDao.getByLoginPassword(login, encodedPassword);
        } catch (DaoException | IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}
