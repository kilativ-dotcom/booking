package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.Request;
import com.example.booking.exceptions.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class AbstractRequestDao implements BaseRequestDao{
    private final WrapperConnection connection;

    public AbstractRequestDao(WrapperConnection connection) {
        this.connection = connection;
    }

    public WrapperConnection getConnection() {
        return connection;
    }

    @Override
    public Optional<Request> getById(int id) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Request> getAll() throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Request> getByParams(Map<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean delete(HashMap<String, String> params) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean insert(Request request) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean update(Request from, Request to) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Request> getUnansweredRequests() throws DaoException {
        throw new UnsupportedOperationException();
    }
}
