package com.example.booking.dao;

import com.example.booking.entity.Suite;
import com.example.booking.exceptions.DaoException;

import java.util.List;

public interface BaseSuiteDao extends BaseDao<Suite> {
    List<Suite> getSuitesForRequestId(int requestId) throws DaoException;
}
