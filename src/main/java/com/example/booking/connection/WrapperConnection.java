package com.example.booking.connection;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

public class WrapperConnection implements Closeable {
    private final Connection connection;

    public WrapperConnection(Connection connection) {
        this.connection = connection;
    }

    public Statement createStatement() throws SQLException {
        return connection.createStatement();
    }

    public PreparedStatement prepareStatement(String sql, Map<Integer, String> parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        fillParameters(statement, parameters);
        return statement;
    }

    public PreparedStatement prepareStatement(String sql, Map<Integer, String> parameters, int generatedKeys) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql, generatedKeys);
        fillParameters(statement, parameters);
        return statement;
    }

    private void fillParameters(PreparedStatement statement, Map<Integer, String> parameters) throws SQLException {
        for (Map.Entry<Integer, String> entry : parameters.entrySet()) {
            statement.setString(entry.getKey(), entry.getValue());
        }
    }

    public PreparedStatement prepareStatementWithUnmodifiableParameters(String sql, Map<String, String> parameters) throws SQLException {
        PreparedStatement statement = connection.prepareStatement(sql);
        fillUnmodifiableParameters(statement, parameters);
        return statement;
    }

    private void fillUnmodifiableParameters(PreparedStatement statement, Map<String, String> parameters) throws SQLException {
        int i = 1;
        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            statement.setString(i++, entry.getValue());
        }
    }

    @Override
    public void close() throws IOException {
        ConnectionPoolImpl.getInstance().releaseConnection(this);
    }
}
