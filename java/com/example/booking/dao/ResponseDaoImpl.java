package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.Request;
import com.example.booking.entity.Response;
import com.example.booking.entity.Suite;
import com.example.booking.entity.User;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class ResponseDaoImpl extends AbstractResponseDao{
    private static final Logger LOGGER = Logger.getLogger(ResponseDaoImpl.class);
//    private WrapperConnection connection;

    public static final String ID_NAME = "id";
    public static final String RESPONSE_TIME_NAME = "responseTime";
    public static final String REQUEST_ID_NAME = "requestId";
    public static final String SUITE_ID_NAME = "suiteId";
    public static final String CREATOR_ID_NAME = "creatorId";
    public static final String TABLE_NAME = "login_test.responses";

    private static final String QUERY_GET_ALL = "select * from " + TABLE_NAME;

    private static final String QUERY_GET_ALL_FOR_REQUEST_CREATOR = "select * from " + TABLE_NAME +
            " join " + RequestDaoImpl.TABLE_NAME + " on " + TABLE_NAME + "." + REQUEST_ID_NAME + "=" + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.ID_NAME +
            " where " + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CREATOR_ID_NAME + "=?;";
    private static final int QUERY_GET_ALL_FOR_REQUEST_CREATOR_ID_INDEX = 1;

    private static final String QUERY_INSERT = "insert into " + TABLE_NAME + " (" + RESPONSE_TIME_NAME + ", " + REQUEST_ID_NAME + ", " + SUITE_ID_NAME + ", " + CREATOR_ID_NAME + ") values(?,?,?,?);";
    private static final int QUERY_INSERT_RESPONSE_TIME_INDEX   = 1;
    private static final int QUERY_INSERT_REQUEST_ID_INDEX      = 2;
    private static final int QUERY_INSERT_SUITE_ID_INDEX        = 3;
    private static final int QUERY_INSERT_CREATOR_ID_INDEX      = 4;

    public ResponseDaoImpl(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public boolean insert(Response response) throws DaoException {
        Map<Integer, String> parametersForPreparedStatement = new HashMap<>();
        parametersForPreparedStatement.put(QUERY_INSERT_RESPONSE_TIME_INDEX,    response.getResponseTime());
        parametersForPreparedStatement.put(QUERY_INSERT_REQUEST_ID_INDEX,       String.valueOf(response.getRequest().getId()));
        parametersForPreparedStatement.put(QUERY_INSERT_SUITE_ID_INDEX,         String.valueOf(response.getSuite().getId()));
        parametersForPreparedStatement.put(QUERY_INSERT_CREATOR_ID_INDEX,       String.valueOf(response.getCreator().getId()));

        try(PreparedStatement statement = getConnection().prepareStatement(QUERY_INSERT, parametersForPreparedStatement, Statement.RETURN_GENERATED_KEYS)){
            int rows = statement.executeUpdate();
            if (rows == 1){
                LOGGER.info("Successfully created response");
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()){
                    int id = generatedKeys.getInt(1);
                    response.setId(id);
                    return true;
                } else{
                    String message = "Cannot get id for generated response";
                    LOGGER.error(message);
                    throw new DaoException(message);
                }
            } else{
                String message = "Unsuccessfully created response";
                LOGGER.error(message);
                throw new DaoException(message);
            }
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot insert response");
        }
    }

    @Override
    public List<Response> getAll() throws DaoException {
        try{
            return getByParams(new HashMap<>());
        } catch (DaoException e){
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get all responses");
        }
    }

    @Override
    public List<Response> getByParams(Map<String, String> params) throws DaoException {
        Map<String, String> unmodifiableLinkedHashMap = Collections.unmodifiableMap(new LinkedHashMap<>(params));
        String whereClause = BaseDao.joinParams(unmodifiableLinkedHashMap);
        String wholeSql = QUERY_GET_ALL + whereClause;
        try(PreparedStatement statement = getConnection().prepareStatementWithUnmodifiableParameters(wholeSql, unmodifiableLinkedHashMap)){
            ResultSet resultSet = statement.executeQuery();
            List<Response> responses = new ArrayList<>();
            while (resultSet.next()){
                responses.add(extractResponseFromResultSet(resultSet));
            }
            return responses;
        } catch (SQLException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get responses by params");
        }
    }

    @Override
    public List<Response> getAllForRequestCreator(int requestCreatorId) throws DaoException{
        Map<Integer, String> parametersForPreparedStatement = new HashMap<>();
        parametersForPreparedStatement.put(QUERY_GET_ALL_FOR_REQUEST_CREATOR_ID_INDEX, String.valueOf(requestCreatorId));
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_GET_ALL_FOR_REQUEST_CREATOR, parametersForPreparedStatement)){
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Response> responses = new ArrayList<>();
            while (resultSet.next()){
                responses.add(extractResponseFromResultSet(resultSet));
            }
            return responses;
        } catch (SQLException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get responses for user requests");
        }

    }

    private Response extractResponseFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
        int     id              = resultSet.getInt(ID_NAME);
        String  responseTime    = resultSet.getString(RESPONSE_TIME_NAME);
        int     requestId       = resultSet.getInt(REQUEST_ID_NAME);
        int     suiteId         = resultSet.getInt(SUITE_ID_NAME);
        int     creatorId       = resultSet.getInt(CREATOR_ID_NAME);

        BaseRequestDao requestDao = new RequestDaoImpl(getConnection());
//        requestDao.setConnection(connection);
        Optional<Request> optionalRequest = requestDao.getById(requestId);
        if (!optionalRequest.isPresent()){
            throw new DaoException("Cannot get request for response");
        }

        BaseSuiteDao suiteDao = new SuiteDaoImpl(getConnection());
//        suiteDao.setConnection(connection);
        Optional<Suite> optionalSuite = suiteDao.getById(suiteId);
        if (!optionalSuite.isPresent()){
            throw new DaoException("Cannot get request for response");
        }

        BaseUserDao userDao = new UserDaoImpl(getConnection());
//        userDao.setConnection(connection);
        Optional<User> optionalUser = userDao.getById(creatorId);
        if (!optionalUser.isPresent()){
            throw new DaoException("Cannot get creator for response");
        }

        return new Response(id, responseTime, optionalRequest.get(), optionalSuite.get(), optionalUser.get());
    }
}
