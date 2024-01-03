package com.livenovel.dev.service.interf;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.book.BookDto;

public interface BookService {
    ResponseDto getById(Long id);
    ResponseDto saveBook(BookDto bookDto);
    ResponseDto updateBook(BookDto bookDto);
    ResponseDto deleteBook(Long id);
}
