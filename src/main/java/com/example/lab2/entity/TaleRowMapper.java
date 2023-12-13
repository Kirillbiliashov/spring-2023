package com.example.lab2.entity;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TaleRowMapper implements RowMapper<Tale> {
    @Override
    public Tale mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        Tale tale = new Tale();
        tale.setId(resultSet.getLong("id"));
        tale.setTitle(resultSet.getString("title"));
        tale.setAuthor(resultSet.getString("author"));
        return tale;
    }
}
