package com.example.booking.connection;

import java.io.Closeable;

public interface ConnectionPool extends Closeable {
    WrapperConnection getConnection();

    void releaseConnection(WrapperConnection connection);
}
