package com.example.booking.dao;

import com.example.booking.entity.Response;
import com.example.booking.exceptions.DaoException;

import java.util.List;

public interface BaseResponseDao extends BaseDao<Response>{

    List<Response> getAllForRequestCreator(int requestCreatorId) throws DaoException;
}
