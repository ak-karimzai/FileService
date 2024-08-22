package com.karimzai.greenatomtask.Infrastructure;

import com.karimzai.greenatomtask.Core.Application.Contracts.Infrastructure.FileStorageService;
import com.karimzai.greenatomtask.Core.Application.Exceptions.ServiceUnavailableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Service
public class FileStorageServiceFS implements FileStorageService {
    @Value("${file-system.storage_dir}")
    private String STORAGE_PATH;

    @Override
    public void save(String content, UUID fileId) throws ServiceUnavailableException {
        try (FileOutputStream fos = new FileOutputStream(STORAGE_PATH + fileId.toString())) {
            fos.write(content.getBytes());
        } catch (IOException e) {
            throw new ServiceUnavailableException("an error occurred in file storage service while saving file");
        }
    }

    @Override
    public String load(UUID fileId) throws ServiceUnavailableException {
        try (FileInputStream fis = new FileInputStream(STORAGE_PATH + fileId.toString())) {
            return new String(fis.readAllBytes());
        } catch (IOException e) {
            throw new ServiceUnavailableException("an error occurred in file storage service while loading file");
        }
    }
}
