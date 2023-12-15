package com.example.lab2.repository;

import com.example.lab2.entity.Tale;
import com.example.lab2.entity.TaleRowMapper;
import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Collection;
import java.util.List;

@Repository
@Primary
@AllArgsConstructor
public class OracleTaleRepository implements TaleRepository {
    private final JdbcTemplate jdbcTemplate;
    private static final String INSERT_TALE = "INSERT INTO TALES (id, title, author) VALUES (?, ?, ?)";

    private static final String UPDATE_TALE = "UPDATE TALES SET title = ?, author = ? WHERE id = ?";

    private static final String FIND_ALL = "SELECT t.id, t.title, t.author, COUNT(l.tale_id) AS likes " +
                                           "FROM TALES t " +
                                           "LEFT JOIN TALES_LIKES l ON t.id = l.tale_id " +
                                           "GROUP BY t.id, t.title, t.author " +
                                           "ORDER BY t.id";
    private static final String FIND_BY_ID = "SELECT t.id, t.title, t.author, COUNT(l.tale_id) AS likes " +
                                             "FROM TALES t " +
                                             "LEFT JOIN TALES_LIKES l ON t.id = l.tale_id " +
                                             "WHERE t.id = ? " +
                                             "GROUP BY t.id, t.title, t.author";
    private static final String DELETE_TALES_LIKES_BY_ID = "DELETE FROM tales_likes WHERE tale_id = ?";
    private static final String DELETE_TALES_READS_BY_ID = "DELETE FROM tales_reads WHERE tale_id = ?";
    private static final String DELETE_TALE_BY_ID = "DELETE FROM tales WHERE id = ?";


    private static final String DELETE_BY_ID = "DELETE FROM tales_likes WHERE tale_id = ?;" +
            "DELETE FROM tales_reads WHERE tale_id = ?;" +
            "DELETE FROM tales WHERE id = ?";

    private static final String SEARCH_BY_CRITERIA = "SELECT * FROM TALES WHERE title LIKE ? OR author LIKE ?";

    public static Tale mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        int likes = rs.getInt("likes");

        Tale tale = new Tale(id, title, author);
        tale.setLikes(likes);

        return tale;
    }
    @Override
    public Tale findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID, OracleTaleRepository::mapRow, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new OpenApiResourceNotFoundException("Tale id=" + id + " not found");
        }
    }

    @Override
    public List<Tale> findAll() {
        return jdbcTemplate.query(FIND_ALL, OracleTaleRepository::mapRow);
    }

    @Override
    public Tale save(Tale tale) {
        if(update(tale) == 1 ) return tale;
        return create(tale);
    }
    public Tale create(Tale tale) {


        KeyHolder keyHolder = new GeneratedKeyHolder();
        PreparedStatementCreatorFactory pscf = new PreparedStatementCreatorFactory(INSERT_TALE,
                Types.NUMERIC, Types.VARCHAR, Types.VARCHAR);
        pscf.setGeneratedKeysColumnNames("ID");

        PreparedStatementCreator psc = pscf.newPreparedStatementCreator(
                new Object[] {tale.getId(), tale.getTitle(), tale.getAuthor()}
        );
        jdbcTemplate.update(psc, keyHolder);
        Long newId = keyHolder.getKey().longValue();
        return new Tale(newId, tale.getTitle(), tale.getAuthor());
    }

    public int update(Tale tale) {
        return jdbcTemplate.update(UPDATE_TALE, tale.getTitle(), tale.getAuthor(), tale.getId());
    }
    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(DELETE_TALES_LIKES_BY_ID, id);
        jdbcTemplate.update(DELETE_TALES_READS_BY_ID, id);
        int rowsAffected = jdbcTemplate.update(DELETE_TALE_BY_ID, id);
        if (rowsAffected == 0) {
            throw new OpenApiResourceNotFoundException("Tale id=" + id + " not found");
        }
    }

    @Override
    public Collection<Tale> findTaleByCriteria(String criteria) {
        return jdbcTemplate.query(SEARCH_BY_CRITERIA, new TaleRowMapper(), new Object[]{"%" + criteria + "%", "%" + criteria + "%"});
    }
}
