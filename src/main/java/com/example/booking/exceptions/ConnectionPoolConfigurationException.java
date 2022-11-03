package com.example.booking.exceptions;

public class ConnectionPoolConfigurationException extends RuntimeException {

    public ConnectionPoolConfigurationException() {
        this(null);
    }

    public ConnectionPoolConfigurationException(String filePath) {
        this(filePath, null);
    }

    public ConnectionPoolConfigurationException(String filePath, Throwable cause) {
        super(
                filePath != null ? "Cannot configure properties using file " + filePath : "",
                cause
        );
    }
}
