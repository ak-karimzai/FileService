package com.karimzai.greenatomtask.API.Controllers.V1;

import com.karimzai.greenatomtask.API.Models.ErrorResponse;
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
import com.karimzai.greenatomtask.Core.Application.Services.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/files")
@Tag(name = "Translations")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping
    @Operation(description = "Загрузить файл.", summary = "Загрузить файл")
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Файл успешно сохранен.", responseCode = "201",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UploadedFileDto.class))),
                    @ApiResponse(description = "Длина заголовок файла должна быть от 1 до 60.", responseCode = "400",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(description = "Сервис недоступна.", responseCode = "503",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<?> uploadFile(@RequestBody UploadFileCommand command) throws ServiceUnavailableException {
        UploadFileCommandResponse response;

        response = fileService.uploadFile(command);

        if (response.isSuccess()) {
            return new ResponseEntity<>(response.getFileDto(), HttpStatus.CREATED);
        } else {
            return ResponseEntity.badRequest().body(response.getValidationErrors());
        }
    }

    @GetMapping("/{fileId}")
    @Operation(description = "Получить файл с его подробностями по идентификатору.", summary = "Получить файл по идентификатору.")
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Файл с идентификатором найден.", responseCode = "200",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = FileDto.class))),
                    @ApiResponse(description = "Неверный идентификатор.", responseCode = "400",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(description = "Файл 00000-000000-0000000-0000000 не найден.", responseCode = "404", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Сервис недоступна.", responseCode = "503",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<FileDto> loadFile(@PathVariable String fileId)
            throws ValidationException, NotFoundException, ServiceUnavailableException {
        var request = new GetFileByIdQuery(fileId);

        FileDto response = fileService.getFileById(request);

        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Operation(description = "Получить список файлов.", summary = "получить постраничный список файлов.")
    @ApiResponses(
            value = {
                    @ApiResponse(description = "Получить список файлов.", responseCode = "200",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = FileDto.class)))),
                    @ApiResponse(description = "Неверный идентификатор.", responseCode = "400",
                            content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = String.class)))),
                    @ApiResponse(description = "Файл с таком идентификатором не найден.", responseCode = "404",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
                    @ApiResponse(description = "Сервис недоступна.", responseCode = "503",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public ResponseEntity<PagedFilesListVm> getFileList(@RequestParam(required = false, defaultValue = "1") int page,
                                         @RequestParam(required = false, defaultValue = "10") int size,
                                         @RequestParam(required = false, defaultValue = "false") boolean orderByCreation) throws ValidationException {
        var request = new GetPagedFileListQuery(page, size, orderByCreation);

        PagedFilesListVm response = fileService.getPagedFileList(request);

        return ResponseEntity.ok(response);
    }
}
