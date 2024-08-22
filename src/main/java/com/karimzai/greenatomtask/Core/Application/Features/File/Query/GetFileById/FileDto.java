package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById;

import java.sql.Timestamp;
import java.util.UUID;

public class FileDto {
    private UUID id;
    private String title;
    private String description;
    private String content;
    private Timestamp uploadDate;

    public FileDto() {
    }

    public FileDto(UUID id, String title, String description, String content, Timestamp uploadDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.content = content;
        this.uploadDate = uploadDate;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Timestamp uploadDate) {
        this.uploadDate = uploadDate;
    }
}
