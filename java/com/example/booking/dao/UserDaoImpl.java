package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class UserDaoImpl extends AbstractUserDao {
    private static final Logger LOGGER = Logger.getLogger(UserDaoImpl.class);

//    private WrapperConnection connection;
    private static final String ID_NAME = "id";
    private static final String LOGIN_NAME = "email";
    private static final String PASSWORD_NAME = "password";
    private static final String IS_ADMIN_NAME = "isAdmin";
    private static final String TABLE_NAME = "login_test.users";

    private static final String QUERY_SELECT_ALL = "select * from " + TABLE_NAME;

    private static final String QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN = "insert into " + TABLE_NAME + " (" + LOGIN_NAME + ", " + PASSWORD_NAME + ", " + IS_ADMIN_NAME + ") VALUES (?, ?, ?);";
    private static final int QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN_LOGIN_INDEX = 1;
    private static final int QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN_PASSWORD_INDEX = 2;
    private static final int QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN_IS_ADMIN_INDEX = 3;

    public UserDaoImpl(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public Optional<User> getById(int id) throws DaoException {
        Map<String, String> nameToValue = new HashMap<>();
        nameToValue.put(ID_NAME, String.valueOf(id));
        try {
            List<User> users = getByParams(nameToValue);
            if (users.isEmpty()) {
                return Optional.empty();
            }
            if (users.size() > 1) {
                throw new DaoException("More than 1 user have id " + id);
            }
            return Optional.of(users.get(0));
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get user by id " + id);
        }
    }

    @Override
    public Optional<User> getByLoginPassword(String login, String password) throws DaoException {
        Map<String, String> nameToValue = new HashMap<>();
        nameToValue.put(LOGIN_NAME, login);
        nameToValue.put(PASSWORD_NAME, password);
        try {
            List<User> users = getByParams(nameToValue);
            if (users.isEmpty()) {
                return Optional.empty();
            }
            if (users.size() > 1) {
                throw new DaoException("More than 1 user have login " + login);
            }
            return Optional.of(users.get(0));
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException(String.format("Cannot get user by login and password(login=%s)%n", login));
        }
    }

    @Override
    public List<User> getAll() throws DaoException {
        try {
            return getByParams(new HashMap<>());
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get all users");
        }
    }

    @Override
    public List<User> getByParams(Map<String, String> params) throws DaoException {
        Map<String, String> unmodifiableLinkedHashMap = Collections.unmodifiableMap(new LinkedHashMap<>(params));
        String whereClause = BaseDao.joinParams(unmodifiableLinkedHashMap);
        String wholeSql = QUERY_SELECT_ALL + whereClause;
        try (PreparedStatement statement = getConnection().prepareStatementWithUnmodifiableParameters(wholeSql, unmodifiableLinkedHashMap)) {
            ResultSet resultSet = statement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(extractUserFromResultSet(resultSet));
            }
            return users;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get users by parameters");
        }
    }

    @Override
    public Optional<User> addUser(String login, String password, boolean isAdmin) throws DaoException {
        Map<String, String> nameToValue = new HashMap<>();
        nameToValue.put(LOGIN_NAME, login);

        synchronized (UserDaoImpl.class) {
            List<User> users = getByParams(nameToValue);
            if (!users.isEmpty()) {
                throw new DaoException(String.format("Login %s is already taken%n", login));
            }
        }

        String adminSetting = String.valueOf(isAdmin ? 1 : 0);

        Map<Integer, String> parametersForPreparedStatement = new HashMap<>();
        parametersForPreparedStatement.put(QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN_LOGIN_INDEX, login);
        parametersForPreparedStatement.put(QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN_PASSWORD_INDEX, password);
        parametersForPreparedStatement.put(QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN_IS_ADMIN_INDEX, adminSetting);

        try (PreparedStatement statement = getConnection().prepareStatement(QUERY_INSERT_LOGIN_PASSWORD_IS_ADMIN, parametersForPreparedStatement, Statement.RETURN_GENERATED_KEYS)) {
            int rows = statement.executeUpdate();
            if (rows == 1) {
                LOGGER.info("Successful adding for user" + login);
                ResultSet generatedKeys = statement.getGeneratedKeys();
                int id;
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    LOGGER.error("User is created but cannot get their id");
                    throw new SQLException();
                }
                return Optional.of(new User(id, login, isAdmin));
            } else {
                LOGGER.info("Unsuccessful adding for user" + login);
                return Optional.empty();
            }
        } catch (SQLException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot add user with login " + login);
        }
    }

    private User extractUserFromResultSet(ResultSet resultSet) throws SQLException {
        int     id      = resultSet.getInt(ID_NAME);
        String  login   = resultSet.getString(LOGIN_NAME);
        boolean isAdmin = (resultSet.getInt(IS_ADMIN_NAME) != 0);
        return new User(id, login, isAdmin);
    }
}
