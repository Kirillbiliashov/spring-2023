package com.example.lab2.repository;

import com.example.lab2.entity.User;
import com.example.lab2.entity.UserRowMapper;
import lombok.AllArgsConstructor;
import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;

@Repository
@Primary
@AllArgsConstructor
public class OracleUserRepository implements UserRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String FIND_ALL = "SELECT id, name, email FROM users";
    private static final String FIND_BY_ID = "SELECT id, name, email FROM users WHERE id=?";
    private static final String INSERT_USER = "INSERT INTO users (name, email) VALUES (?, ?)";
    private static final String DELETE_BY_ID = "DELETE FROM users WHERE id=?";

    private static final String SEARCH_BY_CRITERIA = "SELECT * FROM users WHERE name LIKE ?  OR email LIKE ?";


    private static User mapRow (ResultSet rs, int rowNum) throws SQLException {
        return new User(rs.getLong(1), rs.getString(2), rs.getString(3));
    }
    @Override
    public User findById(Long id) {
        try {
            return jdbcTemplate.queryForObject(FIND_BY_ID, OracleUserRepository::mapRow, id);
        } catch (IncorrectResultSizeDataAccessException e) {
            throw new OpenApiResourceNotFoundException("User id=" + id + " not found");
        }
    }

    @Override
    public List<User> findAll() {
        return jdbcTemplate.query(FIND_ALL, OracleUserRepository::mapRow);
    }

    @Override
    public User save(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            return ps;
        }, keyHolder);
        Long generatedId = Objects.requireNonNull(keyHolder.getKey()).longValue();
        return new User(generatedId, user.getName(), user.getEmail());
    }

    @Override
    public void deleteById(Long id) {
        int rowsAffected = jdbcTemplate.update(DELETE_BY_ID, id);
        if (rowsAffected == 0) {
            throw new OpenApiResourceNotFoundException("User id=" + id + " not found");
        }
    }

    @Override
    public List<User> findUserByCriteria(String criteria) {
        return jdbcTemplate.query(SEARCH_BY_CRITERIA, new UserRowMapper(), new Object[]{"%" + criteria + "%", "%" + criteria + "%"});
    }

}