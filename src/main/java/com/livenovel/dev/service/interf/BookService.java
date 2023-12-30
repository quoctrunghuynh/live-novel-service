package com.livenovel.dev.service.interf;

import com.livenovel.dev.payload.ResponseDto;

public interface BookService {
    ResponseDto getById(Long id);
}
