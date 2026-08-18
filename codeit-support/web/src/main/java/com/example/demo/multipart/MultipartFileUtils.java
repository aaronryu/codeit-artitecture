package com.example.demo.multipart;

import java.util.Objects;

public final class MultipartFileUtils {

    public static String sanitize(String original) {
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
}
