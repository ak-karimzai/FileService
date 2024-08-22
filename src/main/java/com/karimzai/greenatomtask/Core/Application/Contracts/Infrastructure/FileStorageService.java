package com.karimzai.greenatomtask.Core.Application.Contracts.Infrastructure;

import com.karimzai.greenatomtask.Core.Application.Exceptions.ServiceUnavailableException;

import java.util.UUID;

public interface FileStorageService {
    void save(String content, UUID fileId) throws ServiceUnavailableException;
    String load(UUID fileId) throws ServiceUnavailableException;
}
