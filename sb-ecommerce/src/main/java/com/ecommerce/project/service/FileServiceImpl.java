package com.ecommerce.project.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadProductImage(String path, MultipartFile file) throws IOException {
        // File names of the current/ original file
        String originalFileName = file.getOriginalFilename();
        // generate a random file name
        String randomFileName = UUID.randomUUID().toString();
        String fileName = null;
        if (originalFileName != null) {
            fileName = randomFileName.concat(originalFileName.substring(originalFileName.lastIndexOf(".")));
        }
        String fullPath = path + File.separator + fileName;
        // Check if the directory exists or not
        File dir = new File(path);
        if(!dir.exists()){
            dir.mkdirs();
        }
        // upload the file to the folder
        Files.copy(file.getInputStream(), Paths.get(fullPath));

        return fileName;
    }
}
