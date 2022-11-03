package com.example.booking.service;

import com.example.booking.connection.ConnectionPoolImpl;
import com.example.booking.connection.WrapperConnection;
import com.example.booking.dao.BaseRequestDao;
import com.example.booking.dao.RequestDaoImpl;
import com.example.booking.entity.Request;
import com.example.booking.entity.SuiteClass;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class RequestsService {
    private static final Logger LOGGER = Logger.getLogger(RequestsService.class);
    private static RequestsService instance;

    public static RequestsService getInstance() {
        if (instance == null) {
            synchronized (RequestsService.class) {
                if (instance == null) {
                    instance = new RequestsService();
                }
            }
        }
        return instance;
    }

    private RequestsService() {
    }

    public Optional<Request> creteRequest(User user, String checkInDate, String checkOutDate, int guests, String comment, SuiteClass suiteClass) {
        LOGGER.trace("creating request");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String time = dtf.format(now);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Request request = new Request(
                -1,
                time,
                checkInDate,
                checkOutDate,
                guests,
                suiteClass,
                comment,
                false,
                user
        );

        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseRequestDao requestDao = new RequestDaoImpl(connection);
            if (requestDao.insert(request)) {
                return Optional.of(request);
            } else {
                LOGGER.error("cannot create request in database");
                return Optional.empty();
            }
        } catch (DaoException | IOException e) {
            LOGGER.error(e.getMessage());
            return Optional.empty();
        }
    }

    public List<Request> getRequestsFromUser(int userId) {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            Map<String, String> params = new HashMap<>();
            params.put(RequestDaoImpl.CREATOR_ID_NAME, String.valueOf(userId));
            BaseRequestDao requestDao = new RequestDaoImpl(connection);
            return requestDao.getByParams(params);
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public void cancelRequestById(int requestId) {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseRequestDao requestDao = new RequestDaoImpl(connection);
            Optional<Request> optionalRequest = requestDao.getById(requestId);
            if (!optionalRequest.isPresent()) {
                LOGGER.warn(String.format("Request with id %d does not exist%n", requestId));
                return;
            }
            Request oldRequest = optionalRequest.get();
            if (oldRequest.isCancelled()) {
                LOGGER.warn(String.format("Request with id %d is already cancelled%n", requestId));
                return;
            }
            Request newRequest = new Request(
                    oldRequest.getId(),
                    oldRequest.getRequestTime(),
                    oldRequest.getCheckInDate(),
                    oldRequest.getCheckOutDate(),
                    oldRequest.getGuests(),
                    oldRequest.getSuiteClass(),
                    oldRequest.getComment(),
                    true,
                    oldRequest.getCreator()
            );

            if (requestDao.update(oldRequest, newRequest)) {
                LOGGER.info(String.format("Request with id %d was cancelled%n", requestId));
            } else {
                LOGGER.warn(String.format("Cannot cancel request with id %d%n", requestId));
            }

        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return;
        }
    }

    public List<Request> getAll() {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseRequestDao requestDao = new RequestDaoImpl(connection);
            return requestDao.getAll();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<Request> getUnansweredRequests() {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseRequestDao requestDao = new RequestDaoImpl(connection);
            return requestDao.getUnansweredRequests();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
