package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.SuiteClass;
import com.example.booking.exceptions.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractSuiteClassDao implements BaseSuiteClassDao {
    private final WrapperConnection connection;

    public AbstractSuiteClassDao(WrapperConnection connection) {
        this.connection = connection;
    }

    public WrapperConnection getConnection() {
        return connection;
    }

    @Override
    public Optional<SuiteClass> getById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SuiteClass> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SuiteClass> getByParams(Map<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(HashMap<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(SuiteClass suiteClass) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(SuiteClass from, SuiteClass to) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
