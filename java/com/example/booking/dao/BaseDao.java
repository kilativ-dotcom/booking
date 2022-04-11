package com.example.booking.dao;

import com.example.booking.exceptions.DaoException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public interface BaseDao<T> {
    Optional<T> getById(int id) throws DaoException;

    List<T> getAll() throws DaoException;

    List<T> getByParams(Map<String, String> params) throws DaoException;

    boolean delete(HashMap<String, String> params) throws DaoException;

    boolean insert(T t) throws DaoException;

    boolean update(T from, T to) throws DaoException;

    static String joinParams(Map<String, String> params) {// columnName -> value
        if (params.isEmpty()){
            return "";
        }
        String delimiterToJoinKeysAndWildcard = "=";
        String finalDelimiter = " and ";
        String prefix = " where ";
        String suffix = "";
        String wildCard = "?";

        return params.keySet()
                .stream()
                .map(s -> String.join(delimiterToJoinKeysAndWildcard, s, wildCard))
                .collect(Collectors.joining(finalDelimiter, prefix, suffix));

//        return params.keySet()
//                    .stream()
//                    .map(key -> String.join(delimiterToJoinKeysAndWildcard, key, wildcard))
//                    .collect(Collectors.joining(finalDelimiter, prefix, suffix));
    }
}
