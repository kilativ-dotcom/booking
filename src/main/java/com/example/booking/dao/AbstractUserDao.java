package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractUserDao implements BaseUserDao{
    private final WrapperConnection connection;

    public AbstractUserDao(WrapperConnection connection) {
        this.connection = connection;
    }

    public WrapperConnection getConnection() {
        return connection;
    }

    @Override
    public Optional<User> getById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> getByParams(Map<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(HashMap<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(User user) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(User from, User to) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> getByLoginPassword(String login, String password) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> addUser(String login, String password) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
