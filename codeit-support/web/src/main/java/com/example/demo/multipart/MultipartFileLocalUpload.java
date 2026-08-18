package com.example.demo.multipart;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
public class MultipartFileLocalUpload extends MultipartFileAbstractUpload {
    private final String directory;

    @Override
    protected String generate(MultipartFile file) {
        String original = file.getOriginalFilename();
        String sanitized = MultipartFileUtils.sanitize(original);
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
