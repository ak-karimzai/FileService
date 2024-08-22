package com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand;

public class UploadFileCommand {
    private String content;
    private String title;
    private String Description;

    public UploadFileCommand(String content, String title, String description) {
        this.content = content;
        this.title = title;
        Description = description;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }
}
