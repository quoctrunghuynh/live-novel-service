package com.livenovel.dev.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private HttpStatus status;
    private String message;
    private Object data;

    public static ResponseDto getDefaultSucceed(Object data) {
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("Success")
                .data(data)
                .build();
    }

    public static ResponseDto getDefaultFailed(Object data) {
        return ResponseDto.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message("Failed")
                .data(data)
                .build();
    }
}