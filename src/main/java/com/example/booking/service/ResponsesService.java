package com.example.booking.service;

import com.example.booking.connection.ConnectionPoolImpl;
import com.example.booking.connection.WrapperConnection;
import com.example.booking.dao.*;
import com.example.booking.entity.Request;
import com.example.booking.entity.Response;
import com.example.booking.entity.Suite;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResponsesService {
    private static final Logger LOGGER = Logger.getLogger(ResponsesService.class);

    public static List<Response> getResponsesForUserRequests(int requestCreatorId) {
        LOGGER.trace("getting responses for user requests");

        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseResponseDao responseDao = new ResponseDaoImpl(connection);
//            responseDao.setConnection(connection);
            return responseDao.getAllForRequestCreator(requestCreatorId);
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public static Optional<Response> createResponse(int suiteId, int creatorId, int requestId) {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            LOGGER.trace("creating response");
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String time = dtf.format(now);

            BaseSuiteDao suiteDao = new SuiteDaoImpl(connection);
//            suiteDao.setConnection(connection);
            Optional<Suite> optionalSuite = suiteDao.getById(suiteId);
            if (!optionalSuite.isPresent()) {
                return Optional.empty();
            }

            BaseUserDao userDao = new UserDaoImpl(connection);
//            userDao.setConnection(connection);
            Optional<User> optionalUser = userDao.getById(creatorId);
            if (!optionalUser.isPresent()) {
                return Optional.empty();
            }

            BaseRequestDao requestDao = new RequestDaoImpl(connection);
//            requestDao.setConnection(connection);
            Optional<Request> optionalRequest = requestDao.getById(requestId);
            if (!optionalRequest.isPresent()) {
                return Optional.empty();
            }

            Response response = new Response(-1, time, optionalRequest.get(), optionalSuite.get(), optionalUser.get());

            BaseResponseDao responseDao = new ResponseDaoImpl(connection);
//            responseDao.setConnection(connection);
            if (responseDao.insert(response)){
                return Optional.of(response);
            } else {
                return Optional.empty();
            }

        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public static List<Response> getAll(){
        try(WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()){
            BaseResponseDao responseDao = new ResponseDaoImpl(connection);
//            responseDao.setConnection(connection);
            return responseDao.getAll();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
