package com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand;

import java.util.UUID;

public class UploadedFileDto {
    private UUID fileId;

    public UploadedFileDto(UUID id) {
        this.fileId = id;
    }

    public void setFileId(UUID fileId) {
        this.fileId = fileId;
    }

    public UUID getFileId() {
        return fileId;
    }
}
