package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.Response;
import com.example.booking.exceptions.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractResponseDao implements BaseResponseDao {
    private final WrapperConnection connection;

    public AbstractResponseDao(WrapperConnection connection) {
        this.connection = connection;
    }

    public WrapperConnection getConnection() {
        return connection;
    }

    @Override
    public Optional<Response> getById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Response> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Response> getByParams(Map<String, String> params) throws DaoException{
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Response> getAllForRequestCreator(int requestCreatorId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(HashMap<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(Response response) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Response from, Response to) throws DaoException {
        throw new UnsupportedOperationException();
    }
}
