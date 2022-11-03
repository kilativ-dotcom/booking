package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.Suite;
import com.example.booking.entity.SuiteClass;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class SuiteDaoImpl extends AbstractSuiteDao{
    private static final Logger LOGGER = Logger.getLogger(SuiteDaoImpl.class);

    private static final String ID_NAME = "id";
    private static final String ROOM_NUMBER_NAME = "roomNumber";
    private static final String MAX_GUESTS_NAME = "maxGuests";
    private static final String SUITE_CLASS_ID_NAME = "suiteClassId";
    private static final String COST_NAME = "costPerNight";
    private static final String TABLE_NAME = "login_test.suites";

    private static final String QUERY_GET_ALL = "select * from " + TABLE_NAME;

    private static final String QUERY_GET_FOR_REQUEST_ID_PREPARE = "select " +
            "@scid := " + SUITE_CLASS_ID_NAME + ", " +
            "@gue := guests, " +
            "@checkIn := checkInDate, " +
            "@checkOut := checkOutDate " +
            "from requests where id=?;";

    private static final String QUERY_GET_FOR_REQUEST_ID_MAIN = "select * from " + TABLE_NAME + " " +
            "where id not in (" +
            "select " + ResponseDaoImpl.SUITE_ID_NAME + " from " + ResponseDaoImpl.TABLE_NAME + " " +
            "join " + RequestDaoImpl.TABLE_NAME + " on " + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.ID_NAME + " = " + ResponseDaoImpl.TABLE_NAME + "." + ResponseDaoImpl.REQUEST_ID_NAME + " " +
            "where " +
            "((datediff(" + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CHECK_IN_DATE_NAME + ", @checkIn) <= 0 and datediff(@checkIn, " + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CHECK_OUT_DATE_NAME + ") <= 0) or " +
            "(datediff(" + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CHECK_IN_DATE_NAME + ", @checkOut) <= 0 and datediff(@checkOut, " + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CHECK_OUT_DATE_NAME + ") <= 0) or " +
            "(datediff(@checkIn, " + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CHECK_IN_DATE_NAME + ") <= 0 and datediff(" + RequestDaoImpl.TABLE_NAME + "." + RequestDaoImpl.CHECK_OUT_DATE_NAME + ", @checkOut) <= 0)) and " +
            "requests.isCancelled=0 " +
            ") and " + TABLE_NAME + "." + SUITE_CLASS_ID_NAME + " = @scid and " + TABLE_NAME + "." + MAX_GUESTS_NAME + ">=@gue; ";
    private static final int QUERY_GET_FOR_REQUEST_ID_ID_INDEX = 1;

    public SuiteDaoImpl(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public Optional<Suite> getById(int id) throws DaoException {
        Map<String, String> nameToValue = new HashMap<>();
        nameToValue.put(ID_NAME, String.valueOf(id));
        try{
            List<Suite> suites = getByParams(nameToValue);
            if (suites.isEmpty()) {
                return Optional.empty();
            }
            if (suites.size() > 1) {
                throw new DaoException("More than 1 suite have id " + id);
            }
            return Optional.of(suites.get(0));
        } catch (DaoException e){
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get suite by id");
        }
    }

    @Override
    public List<Suite> getAll() throws DaoException {
        try{
            return getByParams(new HashMap<>());
        } catch (DaoException e){
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get all suites");
        }
    }

    @Override
    public List<Suite> getByParams(Map<String, String> params) throws DaoException {
        Map<String, String> unmodifiableLinkedHashMap = Collections.unmodifiableMap(new LinkedHashMap<>(params));
        String whereClause = BaseDao.joinParams(unmodifiableLinkedHashMap);
        String wholeSql = QUERY_GET_ALL + whereClause;
        try(PreparedStatement statement = getConnection().prepareStatementWithUnmodifiableParameters(wholeSql, unmodifiableLinkedHashMap)){
            ResultSet resultSet = statement.executeQuery();
            List<Suite> suites = new ArrayList<>();
            while (resultSet.next()){
                suites.add(extractSuiteFromResultSet(resultSet));
            }
            return suites;
        } catch (SQLException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get suites by parameters");
        }
    }

    @Override
    public List<Suite> getSuitesForRequestId(int requestId) throws DaoException {
        Map<Integer, String> parametersForPreparedStatement = new HashMap<>();
        parametersForPreparedStatement.put(QUERY_GET_FOR_REQUEST_ID_ID_INDEX, String.valueOf(requestId));
        try(PreparedStatement preparedStatement = getConnection().prepareStatement(QUERY_GET_FOR_REQUEST_ID_PREPARE, parametersForPreparedStatement)){
            preparedStatement.executeQuery();
            try(Statement statement = getConnection().createStatement()){
                ResultSet resultSet = statement.executeQuery(QUERY_GET_FOR_REQUEST_ID_MAIN);
                List<Suite> suites = new ArrayList<>();
                while (resultSet.next()){
                    suites.add(extractSuiteFromResultSet(resultSet));
                }
                return suites;
            }
        } catch (SQLException | DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get suites for request " + requestId);
        }
    }

    private Suite extractSuiteFromResultSet(ResultSet resultSet) throws SQLException, DaoException {
        int     id              = resultSet.getInt(ID_NAME);
        String  roomNumber      = resultSet.getString(ROOM_NUMBER_NAME);
        int     maxGuests       = resultSet.getInt(MAX_GUESTS_NAME);
        int     suiteClassId    = resultSet.getInt(SUITE_CLASS_ID_NAME);
        int     cost            = resultSet.getInt(COST_NAME);

        BaseSuiteClassDao suiteClassDao = new SuiteClassDaoImpl(getConnection());
        Optional<SuiteClass> optionalSuiteClass = suiteClassDao.getById(suiteClassId);
        if (!optionalSuiteClass.isPresent()){
            throw new DaoException("Cannot get suite class for suite");
        }

        return new Suite(id, roomNumber, maxGuests, optionalSuiteClass.get(), cost);
    }
}
