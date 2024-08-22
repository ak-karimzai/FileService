package com.karimzai.greenatomtask.Persistence.Repositories;

import com.karimzai.greenatomtask.Core.Domain.Entities.FileInfo;
import org.springframework.stereotype.Component;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

@Component
public class FileInfoRowMapper implements RowMapper<FileInfo> {
    @Override
    public FileInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setId(UUID.fromString(rs.getString("id")));
        fileInfo.setTitle(rs.getString("title"));
        fileInfo.setDescription(rs.getString("description"));
        fileInfo.setUploadDate(rs.getTimestamp("upload_date"));
        return fileInfo;
    }
}
