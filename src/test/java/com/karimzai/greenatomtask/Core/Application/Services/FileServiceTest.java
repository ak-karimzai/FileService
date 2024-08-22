package com.karimzai.greenatomtask.Core.Application.Services;

import com.karimzai.greenatomtask.Core.Application.Contracts.Infrastructure.FileStorageService;
import com.karimzai.greenatomtask.Core.Application.Contracts.Persistence.FileInfoRepository;
import com.karimzai.greenatomtask.Core.Application.Exceptions.NotFoundException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ServiceUnavailableException;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ValidationException;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadFileCommand;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadFileCommandResponse;
import com.karimzai.greenatomtask.Core.Application.Features.File.Command.UploadFileCommand.UploadedFileDto;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById.FileDto;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetFileById.GetFileByIdQuery;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList.GetPagedFileListQuery;
import com.karimzai.greenatomtask.Core.Application.Features.File.Query.GetPagedFileList.PagedFilesListVm;
import com.karimzai.greenatomtask.Core.Application.Profiles.FileInfoMapper;
import com.karimzai.greenatomtask.Core.Domain.Entities.FileInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class FileServiceTest {
    private AutoCloseable autoCloseable;

    @Mock
    private FileStorageService fileStorageService;
    @Mock
    private FileInfoRepository fileInfoRepository;
    @InjectMocks
    FileService fileService;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void fileUploadRequestSuccessfully() throws ServiceUnavailableException {
        // Arrange
        UploadFileCommand command = new UploadFileCommand("test", "test", "test");
        FileInfo fileInfo = new FileInfo(null, "test", "test", null);
        UploadedFileDto dto = new UploadedFileDto(UUID.randomUUID());

        when(fileInfoRepository.save(fileInfo)).thenReturn(dto.getFileId());
        doNothing().when(fileStorageService).save(command.getContent(), dto.getFileId());

        // Act
        UploadFileCommandResponse response = fileService.uploadFile(command);

        // Assert
        assertNotNull(response);
        assertTrue(response.isSuccess());
        assertNotNull(response.getFileDto());
        assertNotNull(response.getFileDto().getFileId());
        assertEquals(response.getFileDto().getFileId(), dto.getFileId());
    }

    @Test
    void fileUploadRequestContentIsEmpty() throws ServiceUnavailableException {
        // Arrange
        UploadFileCommand command = new UploadFileCommand("", "test", "test");
        FileInfo fileInfo = new FileInfo(null, "", "test", null);
        String errorMessage = "Пустой файл.";

        // Act
        UploadFileCommandResponse response = fileService.uploadFile(command);

        // Assert
        assertNotNull(response);
        assertFalse(response.isSuccess());
        assertNull(response.getFileDto());
        assertThat(response.getValidationErrors(), not(empty()));
        assertThat(response.getValidationErrors(), hasItem(errorMessage));
    }

    @Test
    void getFileInfoSuccessfully() throws ServiceUnavailableException, ValidationException, NotFoundException {
        // Arrange
        var id = UUID.randomUUID();
        GetFileByIdQuery query = new GetFileByIdQuery(id.toString());
        FileInfo fileInfo = new FileInfo(id, "test", "test", Timestamp.from(Instant.now()));
        String content = "test";
        FileDto fileDto = FileInfoMapper.mapToFileDto(fileInfo, content);

        when(fileInfoRepository.find(id)).thenReturn(Optional.of(fileInfo));
        when(fileStorageService.load(id)).thenReturn(content);

        // Act
        FileDto fileById = fileService.getFileById(query);

        // Assert
        assertNotNull(fileById);
        assertEquals(fileById.getContent(), content);
        assertEquals(fileById.getTitle(), fileInfo.getTitle());
        assertEquals(fileById.getDescription(), fileInfo.getDescription());
    }

    @Test
    void getFileInfoNotFound() throws NotFoundException {
        // Arrange
        var id = UUID.randomUUID();
        GetFileByIdQuery query = new GetFileByIdQuery(id.toString());
        when(fileInfoRepository.find(id)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(NotFoundException.class, () -> fileService.getFileById(query));
    }

    @Test
    void getFileListSuccessfully() throws ValidationException {
        // Arrange
        GetPagedFileListQuery query = new GetPagedFileListQuery(1, 10, false);
        List<FileInfo> files = List.of(new FileInfo(), new FileInfo(), new FileInfo());

        when(fileInfoRepository.pagedList(query.getPage(), query.getSize(), query.isOrderByCreation()))
                .thenReturn(files);
        when(fileInfoRepository.count())
                .thenReturn(files.size());

        // Act
        PagedFilesListVm pagedFileList = fileService.getPagedFileList(query);

        // Assert
        assertNotNull(pagedFileList);
        assertEquals(pagedFileList.getCount(), 3);
        assertNotNull(pagedFileList.getFiles());
        assertEquals(pagedFileList.getFiles().size(), files.size());
    }

    @Test
    void getFileListValidationFailed() throws ValidationException {
        // Arrange
        GetPagedFileListQuery query = new GetPagedFileListQuery(0, 10, false);

        // Act and Assert
        assertThrows(ValidationException.class, () -> fileService.getPagedFileList(query));
    }
}