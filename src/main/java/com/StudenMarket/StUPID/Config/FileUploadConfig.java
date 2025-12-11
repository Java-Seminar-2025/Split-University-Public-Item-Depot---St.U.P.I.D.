package com.StudenMarket.StUPID.Config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileUploadConfig {
    @Value("${file.upload-dir.profile-pictures}")
    private String profilePicturesDir;

    @Value("${file.upload-dir.post-pictures}")
    private String postPicturesDir;

    @PostConstruct
    public void createUploadDirectories() {
        createDirectory(profilePicturesDir);
        createDirectory(postPicturesDir);
    }

    private void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + path);
            }
        }
    }
}