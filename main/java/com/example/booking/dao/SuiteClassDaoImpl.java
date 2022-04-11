package com.example.booking.dao;

import com.example.booking.connection.WrapperConnection;
import com.example.booking.entity.SuiteClass;
import com.example.booking.exceptions.DaoException;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class SuiteClassDaoImpl extends AbstractSuiteClassDao {
    private static final Logger LOGGER = Logger.getLogger(SuiteClassDaoImpl.class);
//    private WrapperConnection connection;

    private static final String ID_NAME = "id";
    private static final String NAME_NAME = "name";
    private static final String TABLE_NAME = "login_test.suiteclasses";

    private static final String QUERY_SELECT_ALL = "select * from " + TABLE_NAME;

    public SuiteClassDaoImpl(WrapperConnection connection) {
        super(connection);
    }

    @Override
    public Optional<SuiteClass> getById(int id) throws DaoException {
        Map<String, String> nameToValue = new HashMap<>();
        nameToValue.put(ID_NAME, String.valueOf(id));
        try{
            List<SuiteClass> suiteClasses = getByParams(nameToValue);
            if (suiteClasses.isEmpty()) {
                return Optional.empty();
            }
            if (suiteClasses.size() > 1) {
                throw new DaoException("More than 1 suite class have id " + id);
            }
            return Optional.of(suiteClasses.get(0));
        } catch (DaoException e){
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get suite class by id");
        }
    }

    @Override
    public List<SuiteClass> getAll() throws DaoException {
        try {
            return getByParams(new HashMap<>());
        } catch (DaoException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get suite classes from database");
        }
    }

    @Override
    public List<SuiteClass> getByParams(Map<String, String> params) throws DaoException {
        Map<String, String> unmodifiableLinkedHashMap = Collections.unmodifiableMap(new LinkedHashMap<>(params));
        String whereClause = BaseDao.joinParams(unmodifiableLinkedHashMap);
        String wholeSql = QUERY_SELECT_ALL + whereClause;
        try(PreparedStatement statement = getConnection().prepareStatementWithUnmodifiableParameters(wholeSql, unmodifiableLinkedHashMap)){
            ResultSet resultSet = statement.executeQuery();
            List<SuiteClass> suiteClasses = new ArrayList<>();
            while (resultSet.next()){
                suiteClasses.add(extractSuiteClassFromResultSet(resultSet));
            }
            return suiteClasses;
        } catch (SQLException e) {
            LOGGER.error(e.getMessage());
            throw new DaoException("Cannot get suite classes by parameters");
        }
    }

    private SuiteClass extractSuiteClassFromResultSet(ResultSet resultSet) throws SQLException {
        int     id      = resultSet.getInt(ID_NAME);
        String  name    = resultSet.getString(NAME_NAME);
        return new SuiteClass(id, name);
    }
}
