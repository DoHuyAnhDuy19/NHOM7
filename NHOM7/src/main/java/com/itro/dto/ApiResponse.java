package com.itro.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse {
    private Boolean success;
    private String message;
    private Object data;
    private String error;

    public static ApiResponse success(String message, Object data) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .data(data)
                .build();
    }

    public static ApiResponse success(String message) {
        return ApiResponse.builder()
                .success(true)
                .message(message)
                .data(null)
                .build();
    }

    public static ApiResponse error(String message, String error) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .error(error)
                .data(null)
                .build();
    }

    public static ApiResponse error(String message) {
        return ApiResponse.builder()
                .success(false)
                .message(message)
                .data(null)
                .build();
    }
}
