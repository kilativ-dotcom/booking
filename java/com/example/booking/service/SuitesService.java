package com.example.booking.service;

import com.example.booking.connection.ConnectionPoolImpl;
import com.example.booking.connection.WrapperConnection;
import com.example.booking.dao.BaseSuiteClassDao;
import com.example.booking.dao.BaseSuiteDao;
import com.example.booking.dao.SuiteClassDaoImpl;
import com.example.booking.dao.SuiteDaoImpl;
import com.example.booking.entity.Suite;
import com.example.booking.entity.SuiteClass;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SuitesService {
    private static final Logger LOGGER = Logger.getLogger(SuitesService.class);
    private static SuitesService instance;

    public static SuitesService getInstance() {
        if (instance == null) {
            synchronized (SuitesService.class) {
                if (instance == null) {
                    instance = new SuitesService();
                }
            }
        }
        return instance;
    }

    private SuitesService() {
    }

    public List<Suite> getPossibleSuitesForRequest(int requestId) {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseSuiteDao suiteDao = new SuiteDaoImpl(connection);
            return suiteDao.getSuitesForRequestId(requestId);
        } catch (IOException | DaoException e) {
            return new ArrayList<>();
        }
    }

    public List<Suite> getAllSuites() {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseSuiteDao suiteDao = new SuiteDaoImpl(connection);
            return suiteDao.getAll();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    public List<SuiteClass> getAllSuiteClasses() {
        try (WrapperConnection connection = ConnectionPoolImpl.getInstance().getConnection()) {
            BaseSuiteClassDao suiteClassDao = new SuiteClassDaoImpl(connection);
            return suiteClassDao.getAll();
        } catch (IOException | DaoException e) {
            LOGGER.error(e.getMessage());
            return new ArrayList<>();
        }
    }
}
