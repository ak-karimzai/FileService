package com.karimzai.greenatomtask.Core.Application.Contracts.Persistence;

import com.karimzai.greenatomtask.Core.Domain.Entities.FileInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FileInfoRepository {
    UUID save(FileInfo fileInfo);
    Optional<FileInfo> find(UUID id);
    void delete(UUID id);
    List<FileInfo> pagedList(int page, int size, boolean orderByCreation);
    int count();
}
