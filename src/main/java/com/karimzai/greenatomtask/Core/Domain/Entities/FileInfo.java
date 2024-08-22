package com.karimzai.greenatomtask.Core.Domain.Entities;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

public class FileInfo {
    private UUID id;
    private String title;
    private String description;
    private Timestamp uploadDate;

    public FileInfo() {
    }

    public FileInfo(UUID id, String title, String description, Timestamp uploadedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.uploadDate = uploadedAt;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileInfo fileInfo = (FileInfo) o;
        return Objects.equals(id, fileInfo.id) && Objects.equals(title, fileInfo.title) && Objects.equals(description, fileInfo.description) && Objects.equals(uploadDate, fileInfo.uploadDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, uploadDate);
    }
}
