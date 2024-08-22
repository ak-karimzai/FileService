package com.karimzai.greenatomtask.Persistence.Repositories;

import com.karimzai.greenatomtask.Core.Application.Contracts.Persistence.FileInfoRepository;
import com.karimzai.greenatomtask.Core.Domain.Entities.FileInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileInfoRepositoryPsql implements FileInfoRepository {
    private final JdbcTemplate jdbcTemplate;
    private final FileInfoRowMapper fileInfoRowMapper;

    public FileInfoRepositoryPsql(JdbcTemplate jdbcTemplate, FileInfoRowMapper fileInfoRowMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.fileInfoRowMapper = fileInfoRowMapper;
    }

    @Override
    public UUID save(FileInfo fileInfo) {
        String query = "INSERT INTO file_info(title, description) " +
                       "VALUES (?, ?) " +
                       "RETURNING id, upload_date";

        jdbcTemplate.execute(query, (PreparedStatementCallback<FileInfo>) ps -> {
            ps.setString(1, fileInfo.getTitle());
            ps.setString(2, fileInfo.getDescription());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                fileInfo.setId(UUID.fromString(rs.getString("id")));
                fileInfo.setUploadDate(rs.getTimestamp("upload_date"));
                return fileInfo;
            } else {
                throw new SQLException("An error occurred while saving file info in database");
            }
        });
        return fileInfo.getId();
    }

    @Override
    public Optional<FileInfo> find(UUID id) {
        String query = "SELECT id, title, description, upload_date " +
                       "FROM file_info WHERE id = ?";
        return jdbcTemplate.query(query, fileInfoRowMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void delete(UUID id) {
        String query = "DELETE FROM file_info WHERE id = ?";
        jdbcTemplate.update(query, id);
    }

    @Override
    public List<FileInfo> pagedList(int page, int size, boolean orderByCreation) {
        String query = "SELECT id, title, description, upload_date " +
                       "FROM file_info ";

        if (orderByCreation) {
            query += " ORDER BY upload_date DESC";
        }
        query += " LIMIT ? OFFSET ?";
        int offset = (page - 1) * size;
        return jdbcTemplate.query(query, fileInfoRowMapper, size, offset);
    }

    @Override
    public int count() {
        String query = "SELECT count(*) FROM file_info";
        return jdbcTemplate.queryForObject(query, Integer.class);
    }
}
