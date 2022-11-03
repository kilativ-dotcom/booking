package com.example.booking.connection;

import com.example.booking.exceptions.ConnectionPoolConfigurationException;
import org.apache.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionPoolImpl implements ConnectionPool {

    private static final Logger LOGGER = Logger.getLogger(ConnectionPoolImpl.class);

    private final BlockingQueue<WrapperConnection> availableConnections;
    private final BlockingQueue<WrapperConnection> usedConnections;
    private static int POOL_SIZE;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static synchronized ConnectionPoolImpl getInstance() {
        return ConnectionPoolHolder.pool;
    }

    private static class ConnectionPoolHolder {
        static final ConnectionPoolImpl pool = new ConnectionPoolImpl();
    }

    /***
     *
     * @param propertiesLocation - path to the file with configuration properties
     * @throws  ConnectionPoolConfigurationException    if pool has been already configured successfully or propertiesLocation doesn't contain all required properties or specified file cannot be read or driver class cannot be initialized
     */
    public static void configure(String propertiesLocation) {
        if (URL != null && USER != null && PASSWORD != null && POOL_SIZE != 0) {
            throw new ConnectionPoolConfigurationException();
        }

        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propertiesLocation));
            Class.forName(properties.getProperty("db.driver"));
            URL = properties.getProperty("db.url");
            USER = properties.getProperty("user");
            PASSWORD = properties.getProperty("password");
            POOL_SIZE = Integer.parseInt(properties.getProperty("poolSize"));
            if (URL == null || USER == null || PASSWORD == null || POOL_SIZE == 0) {
                throw new ConnectionPoolConfigurationException(propertiesLocation);
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new ConnectionPoolConfigurationException(propertiesLocation, e);
        }
    }

    private ConnectionPoolImpl() {
        usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                availableConnections.offer(new WrapperConnection(this, DriverManager.getConnection(URL, USER, PASSWORD)));
            } catch (SQLException e) {
                throw new IllegalArgumentException("cannot create connection", e);
            }
        }
    }

    @Override
    public WrapperConnection getConnection() {
        try {
            LOGGER.trace("removing connection from available");
            WrapperConnection connection = availableConnections.take();
            LOGGER.trace("adding connection to used");
            usedConnections.offer(connection);
            return connection;
        } catch (InterruptedException e) {
            LOGGER.error("cannot get connection from available", e);
            throw new IllegalArgumentException();
        }
    }

    @Override
    public void releaseConnection(WrapperConnection connection) {
        boolean isRemoved;
        LOGGER.trace("removing connection from used.");
        isRemoved = usedConnections.remove(connection);

        if (isRemoved) {
            LOGGER.trace("returning connection to available.");
            availableConnections.offer(connection);
        } else{
            LOGGER.warn("Attempt to release connection that is not in used queue.");
        }
    }

    @Override
    public void close() {
        for (WrapperConnection connection : usedConnections) {
            try {
                connection.close();
            } catch (IOException e) {
                LOGGER.error("Cannot close used connection.");
            }
        }
        for (WrapperConnection connection : availableConnections) {
            try {
                connection.close();
            } catch (IOException e) {
                LOGGER.error("Cannot close available connection.");
            }
        }
    }
}
