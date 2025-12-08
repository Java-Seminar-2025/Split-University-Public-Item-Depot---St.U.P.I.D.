package com.StudenMarket.StUPID.Config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class FileUploadConfig {
    @Value("${file.upload-dir}")
    private String uploadDir;

    @Bean
    public boolean createUploadDirectory() {
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (!created) {
                throw new RuntimeException("Failed to create directory: " + uploadDir);
            }
            return true;
        }
        return false;
    }
}