package com.karimzai.greenatomtask.Core.Application.Profiles;

import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadFileCommand;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById.FileDto;
import com.karimzai.greenatomtask.Core.Domain.Entities.FileInfo;
import org.springframework.stereotype.Component;

public class FileInfoMapper {
    public static FileInfo mapUploadFileCommand(UploadFileCommand command) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setTitle(command.getTitle());
        fileInfo.setDescription(command.getDescription());
        return fileInfo;
    }

    public static FileDto mapToFileDto(FileInfo fileInfo, String content) {
        FileDto fileDto = new FileDto();
        fileDto.setId(fileInfo.getId());
        fileDto.setTitle(fileInfo.getTitle());
        fileDto.setDescription(fileInfo.getDescription());
        fileDto.setContent(content);
        fileDto.setUploadDate(fileInfo.getUploadDate());
        return fileDto;
    }
}
