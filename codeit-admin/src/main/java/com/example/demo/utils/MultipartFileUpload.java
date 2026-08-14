package com.example.demo.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

public interface MultipartFileUpload {
    String upload(MultipartFile file);
}
