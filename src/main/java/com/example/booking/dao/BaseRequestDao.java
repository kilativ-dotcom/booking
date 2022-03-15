package com.example.booking.dao;

import com.example.booking.entity.Request;
import com.example.booking.exceptions.DaoException;

import java.util.List;

public interface BaseRequestDao extends BaseDao<Request> {
    List<Request> getUnansweredRequests() throws DaoException;
}
// Map<String, String> fieldToValueMap