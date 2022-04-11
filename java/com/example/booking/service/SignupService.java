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

public class SignupService {
    private static final Logger LOGGER = Logger.getLogger(SignupService.class);
    private static SignupService instance;

    public static SignupService getInstance() {
        if (instance == null) {
            synchronized (SignupService.class) {
                if (instance == null) {
                    instance = new SignupService();
                }
            }
        }
        return instance;
    }

    private SignupService() {
    }

    public Optional<User> signupLoginPassword(String login, String password) {
        LOGGER.trace("user: " + login);
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            String encodedPassword = PasswordEncoder.encode(password);
            BaseUserDao userDao = new UserDaoImpl(connection);
            return userDao.addUser(login, encodedPassword, false);
        } catch (DaoException | IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}
