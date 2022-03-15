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

    public static Optional<User> signupLoginPassword(String login, String password) {
        LOGGER.trace("user: " + login);
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            String encodedPassword = PasswordEncoder.encode(password);
            BaseUserDao userDao = new UserDaoImpl(connection);
//            userDao.setConnection(connection);
            return userDao.addUser(login, encodedPassword);
        } catch (DaoException | IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }
}
