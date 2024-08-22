package com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById;

public class GetFileByIdQuery {
    private String fileId;

    public GetFileByIdQuery(String fileId) {
        this.fileId = fileId;
    }

    public String getFileId() {
        return fileId;
    }
}
