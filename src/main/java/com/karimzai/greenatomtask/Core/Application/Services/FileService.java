package com.karimzai.greenatomtask.Core.Application.Services;

import br.com.fluentvalidator.context.ValidationResult;
import com.karimzai.greenatomtask.Core.Application.Contracts.Infrastructure.FileStorageService;
import com.karimzai.greenatomtask.Core.Application.Contracts.Persistence.FileInfoRepository;
import com.karimzai.greenatomtask.Core.Application.Exceptions.NotFoundException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ServiceUnavailableException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ValidationException;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadFileCommand;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadFileCommandResponse;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadFileCommandValidator;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadedFileDto;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById.FileDto;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById.GetFileByIdQuery;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById.GetFileByIdQueryValidator;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList.GetPagedFileListQuery;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList.GetPagedFileListQueryValidator;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList.PagedFilesListVm;
import com.karimzai.greenatomtask.Core.Application.Profiles.FileInfoMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class FileService {
    private final FileStorageService fileStorageService;
    private final FileInfoRepository fileInfoRepository;

    public FileService(FileStorageService fileStorageService,
            FileInfoRepository fileInfoRepository) {
        this.fileStorageService = fileStorageService;
        this.fileInfoRepository = fileInfoRepository;
    }

    public UploadFileCommandResponse uploadFile(UploadFileCommand command) throws ServiceUnavailableException {
        UploadFileCommandValidator validator = new UploadFileCommandValidator();
        ValidationResult validationResult = validator.validate(command);
        UploadFileCommandResponse response = new UploadFileCommandResponse(validationResult);

        if (response.isSuccess()) {
            var fileInfo = FileInfoMapper.mapUploadFileCommand(command);
            var id = fileInfoRepository.save(fileInfo);
            try {
                fileStorageService.save(command.getContent(), id);
                response.setFileDto(new UploadedFileDto(id));
            } catch (ServiceUnavailableException e) {
                fileInfoRepository.delete(id);
                throw new ServiceUnavailableException("file service unavailable");
            }
        }

        return response;
    }

    public FileDto getFileById(GetFileByIdQuery query) throws ValidationException, NotFoundException, ServiceUnavailableException {
        GetFileByIdQueryValidator validator = new GetFileByIdQueryValidator();
        ValidationResult validationResult = validator.validate(query);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult);
        }

        var fileInfo = fileInfoRepository.find(
                UUID.fromString(query.getFileId()));
        if (fileInfo.isEmpty()) {
            throw new NotFoundException("Файл", query.getFileId());
        }

        String fileContent;
        try {
            fileContent = fileStorageService.load(fileInfo.get().getId());
        } catch (ServiceUnavailableException e) {
            throw new ServiceUnavailableException("file service unavailable");
        }

        return FileInfoMapper.mapToFileDto(fileInfo.get(), fileContent);
    }

    public PagedFilesListVm getPagedFileList(GetPagedFileListQuery query) throws ValidationException {
        GetPagedFileListQueryValidator validator = new GetPagedFileListQueryValidator();
        ValidationResult validationResult = validator.validate(query);

        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult);
        }

        var list = fileInfoRepository.pagedList(
                query.getPage(), query.getSize(), query.isOrderByCreation());
        var count = fileInfoRepository.count();
        return new PagedFilesListVm(count, query.getPage(), query.getSize(), list);
    }
}
