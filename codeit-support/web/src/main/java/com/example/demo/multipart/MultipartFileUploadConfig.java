package com.example.demo.multipart;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MultipartFileUploadConfig {

    @Value("${file.upload-directory:${user.dir}/uploads}")
    private String uploadDirectory;

    @Value("${file.storage-type:local}")
    private MultipartFileStorageType storageType;

    @Bean
    public MultipartFileUpload multipartFileUpload() {
        return switch (storageType) {
            case LOCAL -> new MultipartFileLocalUpload(uploadDirectory);
//          case S3    -> new MultipartFileS3Upload(uploadDirectory);
//          case DUMMY -> new MultipartFileDummyUpload();
            default -> throw new IllegalStateException("Not implemented: " + storageType);
        };
    }
}
