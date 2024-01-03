package com.livenovel.dev.builder;

import com.livenovel.dev.payload.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class ResponseDtoBuilder {
    public ResponseDto build(String message, Object data, HttpStatus status){
        return ResponseDto.builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
