package com.example.demo.multipart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
public abstract class MultipartFileAbstractUpload implements MultipartFileUpload {

    public final String upload(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("파일이 비어있습니다. 업로드를 위한 파일을 보내주셔야합니다");
        }
        String filename = generate(file);
        try {
            return upload(file, filename);
        } catch (IOException e) {
            log.error("파일 저장 시 에러가 발생했습니다 : {}", filename, e);
            return null;
        }
    }

    abstract protected String generate(MultipartFile file);
    abstract protected String upload(MultipartFile file, String filename) throws IOException;
}
