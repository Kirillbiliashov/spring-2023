package com.example.lab2.services;

import com.example.lab2.entity.Tale;
import com.example.lab2.repository.OracleTaleRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TaleServiceImpl implements TaleService {
    private final OracleTaleRepository taleRepository;
    private final JdbcTemplate jdbcTemplate;
    private static final String FIND_BEST = "SELECT t.id, t.title, t.author, COUNT(l.tale_id) AS likes " +
            "FROM TALES t " +
            "LEFT JOIN TALES_LIKES l ON t.id = l.tale_id " +
            "GROUP BY t.id, t.title, t.author " +
            "ORDER BY likes DESC";
    private static final String FIND_FAVORITE  = "SELECT t.id, t.title, t.author, " +
            "(SELECT COUNT(*) FROM TALES_LIKES l WHERE l.tale_id = t.id) AS likes " +
            "FROM TALES t " +
            "WHERE EXISTS (SELECT 1 FROM TALES_LIKES l WHERE l.tale_id = t.id AND l.user_id = ?) " +
            "ORDER BY likes DESC";
    private static final String FIND_UNREAD = "SELECT t.id, t.title, t.author " +
            "FROM TALES t " +
            "LEFT JOIN TALES_READS r ON t.id = r.tale_id AND r.user_id = ? " +
            "WHERE r.tale_id IS NULL";
    private static final String INSERT_READ = "MERGE INTO TALES_READS tr " +
            "USING DUAL " +
            "ON (tr.tale_id = ? AND tr.user_id = ?) " +
            "WHEN NOT MATCHED THEN " +
            "INSERT (tale_id, user_id) VALUES (?, ?)";
    private static final String INSERT_LIKE = "MERGE INTO TALES_LIKES tl " +
            "USING DUAL " +
            "ON (tl.tale_id = ? AND tl.user_id = ?) " +
            "WHEN NOT MATCHED THEN " +
            "INSERT (tale_id, user_id) VALUES (?, ?)";
    private static final String DELETE_LIKE = "DELETE FROM TALES_LIKES WHERE tale_id = ? AND user_id = ?";
    private static final String DELETE_READ = "DELETE FROM TALES_READS WHERE tale_id = ? AND user_id = ?";


    private static Tale mapRow(ResultSet rs, int rowNum) throws SQLException {
        long id = rs.getLong("id");
        String title = rs.getString("title");
        String author = rs.getString("author");
        Tale tale = new Tale(id, title, author);

        return tale;
    }
    public TaleServiceImpl(OracleTaleRepository taleRepository, JdbcTemplate jdbcTemplate) {
        this.taleRepository = taleRepository;
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Tale> findAll() {
        return taleRepository.findAll();
    }
    @Override
    public Tale findById(Long id) {
        return taleRepository.findById(id);
    }
    @Override
    public Tale create(Tale tale) {
        Long taleId = taleRepository.create(tale);
        tale.setId(taleId);
        return tale;
    }
    @Override
    public Tale update(Tale tale) {
        return taleRepository.update(tale);
    }
    @Override
    public void deleteById(Long id) {
        taleRepository.deleteById(id);
    }

    public List<Tale> findBestTales() {
        return jdbcTemplate.query(FIND_BEST, OracleTaleRepository::mapRow);
    }

    public List<Tale> findFavoriteTalesByUserId(Long userId) {

        return jdbcTemplate.query(FIND_FAVORITE, OracleTaleRepository::mapRow, userId);
    }
    public List<Tale> findUnreadTalesByUserId(Long userId) {
        return jdbcTemplate.query(FIND_UNREAD, TaleServiceImpl::mapRow, userId);
    }
    @Transactional
    public void addLikeToTale(Long userId, Long taleId) {
        try {
            jdbcTemplate.update(INSERT_LIKE, taleId, userId, taleId, userId);
        } catch (DataIntegrityViolationException e) {
            e.getMessage();
        }

    }
    @Transactional
    public void removeLikeFromTale(Long userId, Long taleId) {
        try {
            jdbcTemplate.update(DELETE_LIKE, taleId, userId);
        } catch (DataIntegrityViolationException e) {
            e.getMessage();
        }

    }
    @Transactional
    public void addReadToTale(Long userId, Long taleId) {
        try {
            jdbcTemplate.update(INSERT_READ, taleId, userId, taleId, userId);
        } catch (DataIntegrityViolationException e) {
            e.getMessage();
        }

    }
    @Transactional
    public void removeReadFromTale(Long userId, Long taleId) {
        try {
            jdbcTemplate.update(DELETE_READ, taleId, userId);
        } catch (DataIntegrityViolationException e) {
            e.getMessage();
        }

    }

//
//    public Page<Tale> getAllTalesPageWithFilter(PageRequest pageRequest, String filter) {
//        return taleRepository.findTalesPageWithFilter(pageRequest, filter);
//    }
}
