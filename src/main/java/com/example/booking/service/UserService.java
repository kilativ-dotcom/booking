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
    public static List<User> getAll(){
        try(WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()){
            BaseUserDao userDao = new UserDaoImpl(connection);
//            userDao.setConnection(connection);
            return userDao.getAll();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
