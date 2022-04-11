package com.example.booking.connection;

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

    private static ConnectionPoolImpl pool;
    private final BlockingQueue<WrapperConnection> availableConnections;
    private final BlockingQueue<WrapperConnection> usedConnections;
    private static int POOL_SIZE;
    private static String URL;
    private static String USER;
    private static String PASSWORD;

    public static synchronized ConnectionPoolImpl getInstance() {
        LOGGER.trace("requested ConnectionPoolImpl instance.");
        if (pool == null) {
            synchronized (ConnectionPoolImpl.class){
                if(pool == null){
                    LOGGER.info("pool is null. Creating new ConnectionPoolImpl.");
                    pool = new ConnectionPoolImpl();
                }
            }
        }
        return pool;
    }

    public static boolean configure(String propertiesLocation) {
        Properties properties = new Properties();
        try {
            properties.load(new FileReader(propertiesLocation));
            try {
                Class.forName(properties.getProperty("db.driver"));
                URL = properties.getProperty("db.url");
                USER = properties.getProperty("user");
                PASSWORD = properties.getProperty("password");
                POOL_SIZE = Integer.parseInt(properties.getProperty("poolSize"));
                return (URL != null && USER != null && PASSWORD != null && POOL_SIZE != 0);
            } catch (ClassNotFoundException e) {
                LOGGER.error("cannot register driver");
                return false;
            }
        } catch (IOException e) {
            LOGGER.error("File " + propertiesLocation + " was not found");
            return false;
        }
    }

    private ConnectionPoolImpl() {
        usedConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        availableConnections = new ArrayBlockingQueue<>(POOL_SIZE);
        for (int i = 0; i < POOL_SIZE; i++) {
            try {
                availableConnections.offer(new WrapperConnection(this, DriverManager.getConnection(URL, USER, PASSWORD)));
            } catch (SQLException e) {
                LOGGER.fatal("cannot create connection", e);
                throw new IllegalArgumentException();
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
