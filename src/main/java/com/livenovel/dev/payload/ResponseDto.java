package com.livenovel.dev.payload;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private String status;
    private Object data;

    private static final ResponseDto instance = new ResponseDto();

    public static ResponseDto getInstance() {
        return instance;
    }

    public ResponseDto getSuccessResponseDtoWithData(Object data){
        return ResponseDto.builder()
                .status("200")
                .message("Founded, please wait...")
                .data(data)
                .build();
    }

    public ResponseDto getFailResponseDto(){
        return ResponseDto.builder()
                .status("404")
                .message("Not found, please try again...")
                .data(false)
                .build();
    }
}