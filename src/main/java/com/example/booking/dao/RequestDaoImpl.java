package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.Request;
import com.example.booking.entity.SuiteClass;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class RequestDaoImpl extends AbstractRequestDao {
    private static final Logger LOGGER = Logger.getLogger(RequestDaoImpl.class);

//    private WrapperConnection connection;

    public static final String ID_NAME = "id";
    public static final String REQUEST_TIME_NAME = "requestTime";
    public static final String CHECK_IN_DATE_NAME = "checkInDate";
    public static final String CHECK_OUT_DATE_NAME = "checkOutDate";
    public static final String GUESTS_NAME = "guests";
    public static final String SUITE_CLASS_ID_NAME = "suiteClassId";
    public static final String COMMENT_NAME = "comment";
    public static final String IS_CANCELLED_NAME = "isCancelled";
    public static final String CREATOR_ID_NAME = "creatorId";
    public static final String TABLE_NAME = "login_test.requests";

    private static final String QUERY_SELECT_ALL = "select * from " + TABLE_NAME;

    private static final String QUERY_INSERT = "insert into " + TABLE_NAME + " (" + REQUEST_TIME_NAME + ", " + CHECK_IN_DATE_NAME + ", " + CHECK_OUT_DATE_NAME + ", " + GUESTS_NAME + ", " + SUITE_CLASS_ID_NAME + ", " + COMMENT_NAME + ", " + IS_CANCELLED_NAME + ", " + CREATOR_ID_NAME + ") values(?,?,?,?,?,?,?,?);";
    private static final int QUERY_INSERT_REQUEST_TIME_INDEX    = 1;
    private static final int QUERY_INSERT_CHECK_IN_DATE_INDEX   = 2;
    private static final int QUERY_INSERT_CHECK_OUT_DATE_INDEX  = 3;
    private static final int QUERY_INSERT_GUEST_INDEX           = 4;
    private static final int QUERY_INSERT_SUITE_CLASS_ID_INDEX  = 5;
    private static final int QUERY_INSERT_COMMENT_INDEX         = 6;
    private static final int QUERY_INSERT_IS_CANCELLED_INDEX    = 7;
    private static final int QUERY_INSERT_CREATOR_ID_INDEX      = 8;

    private static final String QUERY_UPDATE = "update " + TABLE_NAME + " set " + REQUEST_TIME_NAME + "=?, " + CHECK_OUT_DATE_NAME + "=?, " + CHECK_OUT_DATE_NAME + "=?, " + GUESTS_NAME + "=?, " + SUITE_CLASS_ID_NAME + "=?, " + COMMENT_NAME + "=?, " + IS_CANCELLED_NAME + "=?, " + CREATOR_ID_NAME + "=? where " + ID_NAME + "=?;";
    private static final int QUERY_UPDATE_REQUEST_TIME_INDEX    = 1;
    private static final int QUERY_UPDATE_CHECK_IN_DATE_INDEX   = 2;
    private static final int QUERY_UPDATE_CHECK_OUT_DATE_INDEX  = 3;
    private static final int QUERY_UPDATE_GUEST_INDEX           = 4;
    private static final int QUERY_UPDATE_SUITE_CLASS_ID_INDEX  = 5;
    private static final int QUERY_UPDATE_COMMENT_INDEX         = 6;
    private static final int QUERY_UPDATE_IS_CANCELLED_INDEX    = 7;
    private static final int QUERY_UPDATE_CREATOR_ID_INDEX      = 8;
    private static final int QUERY_UPDATE_ID_INDEX              = 9;

    private static final String QUERY_GET_UNANSWERED_REQUESTS = "select * from " + TABLE_NAME + " " +
            " where " + ID_NAME + " not in (select " + TABLE_NAME + "." + ID_NAME + " from " + TABLE_NAME + " join " + ResponseDaoImpl.TABLE_NAME + " on " + TABLE_NAME + "." + ID_NAME + "=" + ResponseDaoImpl.TABLE_NAME + "." + ResponseDaoImpl.REQUEST_ID_NAME + ");";

    public RequestDaoImpl(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public Optional<Request> getById(int id) throws DaoException {
        Map<String, String> nameToValue = new HashMap<>();
        nameToValue.put(ID_NAME, String.valueOf(id));
        try {
            List<Request> requests = getByParams(nameToValue);
            if (requests.isEmpty()) {
                return Optional.empty();
            }
            if (requests.size() > 1) {
                throw new DaoException("For some reason more than 1 requests have id " + id);
            }
            return Optional.of(requests.get(0));
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get request by id");
        }
    }

    @Override
    public List<Request> getAll() throws DaoException {
        try {
            return getByParams(new HashMap<>());
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get all requests");
        }
    }

    @Override
    public List<Request> getByParams(Map<String, String> params) throws DaoException {
        Map<String, String> unmodifiableLinkedHashMap = Collections.unmodifiableMap(new LinkedHashMap<>(params));
        String whereClause = BaseDao.joinParams(unmodifiableLinkedHashMap);
        String wholeSql = QUERY_SELECT_ALL + whereClause;
        try (PreparedStatement statement = getConnection().prepareStatementWithUnmodifiableParameters(wholeSql, unmodifiableLinkedHashMap)) {
            ResultSet resultSet = statement.executeQuery();
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(extractRequestFromResultSet(resultSet));
            }
            return requests;
        } catch (SQLException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get requests by parameters");
        }
    }

    @Override
    public boolean insert(Request request) throws DaoException {
        Map<Integer, String> parametersForPreparedStatement = new HashMap<>();
        parametersForPreparedStatement.put(QUERY_INSERT_REQUEST_TIME_INDEX,     request.getRequestTime());
        parametersForPreparedStatement.put(QUERY_INSERT_CHECK_IN_DATE_INDEX,    request.getCheckInDate());
        parametersForPreparedStatement.put(QUERY_INSERT_CHECK_OUT_DATE_INDEX,   request.getCheckOutDate());
        parametersForPreparedStatement.put(QUERY_INSERT_GUEST_INDEX,            String.valueOf(request.getGuests()));
        parametersForPreparedStatement.put(QUERY_INSERT_SUITE_CLASS_ID_INDEX,   String.valueOf(request.getSuiteClass().getId()));
        parametersForPreparedStatement.put(QUERY_INSERT_COMMENT_INDEX,          request.getComment());
        parametersForPreparedStatement.put(QUERY_INSERT_IS_CANCELLED_INDEX,     String.valueOf(request.isCancelled() ? 1 : 0));
        parametersForPreparedStatement.put(QUERY_INSERT_CREATOR_ID_INDEX,       String.valueOf(request.getCreator().getId()));

        try (PreparedStatement statement = getConnection().prepareStatement(QUERY_INSERT, parametersForPreparedStatement, Statement.RETURN_GENERATED_KEYS)) {
            int rows = statement.executeUpdate();
            if (rows == 1) {
                LOGGER.info("Successful added request from " + request.getCreator());
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int id = generatedKeys.getInt(1);
                    request.setId(id);
                    return true;
                } else {
                    throw new DaoException("Request is created but cannot get its id");
                }
            } else {
                LOGGER.warn("Unsuccessful added request from " + request.getCreator());
                return false;
            }
        } catch (SQLException | IllegalArgumentException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Error when added request from " + request.getCreator());
        }
    }

    @Override
    public boolean update(Request from, Request to) throws DaoException {
        Map<Integer, String> parametersForPreparedStatement = new HashMap<>();
        parametersForPreparedStatement.put(QUERY_UPDATE_REQUEST_TIME_INDEX,     to.getRequestTime());
        parametersForPreparedStatement.put(QUERY_UPDATE_CHECK_IN_DATE_INDEX,    to.getCheckInDate());
        parametersForPreparedStatement.put(QUERY_UPDATE_CHECK_OUT_DATE_INDEX,   to.getCheckOutDate());
        parametersForPreparedStatement.put(QUERY_UPDATE_GUEST_INDEX,            String.valueOf(to.getGuests()));
        parametersForPreparedStatement.put(QUERY_UPDATE_SUITE_CLASS_ID_INDEX,   String.valueOf(to.getSuiteClass().getId()));
        parametersForPreparedStatement.put(QUERY_UPDATE_COMMENT_INDEX,          to.getComment());
        parametersForPreparedStatement.put(QUERY_UPDATE_IS_CANCELLED_INDEX,     String.valueOf(to.isCancelled() ? 1 : 0));
        parametersForPreparedStatement.put(QUERY_UPDATE_CREATOR_ID_INDEX,       String.valueOf(to.getCreator().getId()));
        parametersForPreparedStatement.put(QUERY_UPDATE_ID_INDEX,               String.valueOf(from.getId()));

        try (PreparedStatement statement = getConnection().prepareStatement(QUERY_UPDATE, parametersForPreparedStatement)) {
            int rows = statement.executeUpdate();
            if (rows == 1) {
                LOGGER.info("Successful update");
                return true;
            } else if (rows == 0) {
                LOGGER.info("Unsuccessful update");
                return false;
            } else {
                throw new DaoException("For some reason more than 1 row was updated(Should be 1 or 0)");
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot update request with id " + from.getId());
        }
    }

    @Override
    public List<Request> getUnansweredRequests() throws DaoException {
        try (Statement statement = getConnection().createStatement()) {
            ResultSet resultSet = statement.executeQuery(QUERY_GET_UNANSWERED_REQUESTS);
            List<Request> requests = new ArrayList<>();
            while (resultSet.next()) {
                requests.add(extractRequestFromResultSet(resultSet));
            }
            return requests;
        } catch (SQLException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get unanswered requests");
        }
    }

    private Request extractRequestFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
        int     id              = resultSet.getInt(ID_NAME);
        String  requestTime     = resultSet.getString(REQUEST_TIME_NAME);
        String  checkInDate     = resultSet.getString(CHECK_IN_DATE_NAME);
        String  checkOutDate    = resultSet.getString(CHECK_OUT_DATE_NAME);
        int     guests          = resultSet.getInt(GUESTS_NAME);
        int     suiteClassId    = resultSet.getInt(SUITE_CLASS_ID_NAME);
        String  comment         = resultSet.getString(COMMENT_NAME);
        boolean isCancelled     = resultSet.getInt(IS_CANCELLED_NAME) != 0;
        int     creatorId       = resultSet.getInt(CREATOR_ID_NAME);

        BaseSuiteClassDao suiteClassDao = new SuiteClassDaoImpl(getConnection());
//        suiteClassDao.setConnection(connection);
        Optional<SuiteClass> optionalSuiteClass = suiteClassDao.getById(suiteClassId);
        if (!optionalSuiteClass.isPresent()) {
            throw new DaoException("Cannot get suite class of request");
        }

        BaseUserDao userDao = new UserDaoImpl(getConnection());
//        userDao.setConnection(connection);
        Optional<User> optionalUser = userDao.getById(creatorId);
        if (!optionalUser.isPresent()) {
            throw new DaoException("Cannot get creator of request");
        }

        return new Request(id, requestTime, checkInDate, checkOutDate, guests, optionalSuiteClass.get(), comment, isCancelled, optionalUser.get());
    }
}
