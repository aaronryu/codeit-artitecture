package com.example.demo.multipart;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
public class MultipartFileLocalUpload extends MultipartFileAbstractUpload {
    private final String directory;

    public MultipartFileLocalUpload(@Value("${file.upload-directory:default}") String directory) {
        /* FIXME : 만약 개발자가 codeit-support:web 서브 모듈을 사용하려하는데, MultipartFileUpload 가 필요앖다면 @Value 디렉토리 설정을 하지 않았을때 해당 Bean 이 생성되지 않게하거나 DefaultBean 생성하도록 개발 수정 필요 */
        this.directory = directory;
    }

    private String sanitize(String original) {
        if (Objects.isNull(original) || original.isBlank()) {
            original = "unknown";
        }
        // Windows 및 Unix 경로 구분자를 기준으로 마지막 파일명만 추출
        int lastWinSep = original.lastIndexOf('\\');
        int lastUnixSep = original.lastIndexOf('/');
        int lastIndex = Math.max(lastWinSep, lastUnixSep);
        // 파일명이 빈 값이라면 unknown 파일명으로 변환하여 반환
        String sanitized = (lastIndex != -1)
                ? original.substring(lastIndex + 1)
                : original;
        return sanitized.isBlank() ? "unknown" : sanitized;
    }

    @Override
    protected String generate(MultipartFile file) {
        String original = file.getOriginalFilename();
        String sanitized = sanitize(original);
        return UUID.randomUUID() + "_" + sanitized;
    }

    @Override
    protected String upload(MultipartFile file, String filename) throws IOException {
        Path uploadDirectory = Path.of(directory).toAbsolutePath();
        Files.createDirectories(uploadDirectory);

        Path destinationPath = uploadDirectory.resolve(filename);
        File destinationFile = destinationPath.toFile();
        file.transferTo(destinationFile);
        return destinationFile.getAbsolutePath();
    }
}
