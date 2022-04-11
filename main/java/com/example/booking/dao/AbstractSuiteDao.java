package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.Suite;
import com.example.booking.exceptions.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractSuiteDao implements BaseSuiteDao{
    private final WrapperConnection connection;

    public AbstractSuiteDao(WrapperConnection connection) {
        this.connection = connection;
    }

    public WrapperConnection getConnection() {
        return connection;
    }

    @Override
    public Optional<Suite> getById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Suite> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Suite> getByParams(Map<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(HashMap<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(Suite suite) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Suite from, Suite to) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Suite> getSuitesForRequestId(int requestId) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
