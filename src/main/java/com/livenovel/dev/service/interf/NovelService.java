package com.livenovel.dev.service.interf;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.novel.NovelDto;
import com.livenovel.dev.payload.novel.request.NovelDeleteRequest;

public interface NovelService {
    ResponseDto getById(Long id);
    ResponseDto saveBook(NovelDto novelDto);
    ResponseDto updateBook(NovelDto novelDto);
    ResponseDto deleteBook(NovelDeleteRequest novelDeleteRequest);
}
