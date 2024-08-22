package com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.greenatomtask.Core.Application.Responses.BaseResponse;

import java.util.UUID;

public class UploadFileCommandResponse extends BaseResponse {
    private UploadedFileDto fileDto;

    public UploadFileCommandResponse() {
    }

    public UploadFileCommandResponse(ValidationResult validationResult) {
        super(validationResult);
    }

    public UploadedFileDto getFileDto() {
        return fileDto;
    }

    public void setFileDto(UploadedFileDto fileDto) {
        this.fileDto = fileDto;
    }
}
