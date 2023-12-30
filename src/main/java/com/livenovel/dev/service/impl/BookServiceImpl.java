package com.livenovel.dev.service.impl;

import com.livenovel.dev.converter.abs.BookConverter;
import com.livenovel.dev.entity.Book;
import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.response.BookDto;
import com.livenovel.dev.repository.BookRepository;
import com.livenovel.dev.repository.redis.BookDtoRedisRepository;
import com.livenovel.dev.service.interf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookDtoRedisRepository redisRepository;
    private final BookConverter bookConverter;
    private final ResponseDto responseDto;
    @Override
    public ResponseDto getById(Long id) {
        Object bookInCache = redisRepository.findBookDtoWithId(id);
        if(bookInCache == null){
        Optional<Book> book = bookRepository.findBookByIdAndIsDeletedIsFalse(id);
            if(book.isEmpty()){
                return ResponseDto.builder()
                        .status("404")
                        .message("No book found, please try again...")
                        .data(false)
                        .build();
            }
            else{
                BookDto bookDto = bookConverter.convert(book);
                if(bookDto == null){
                    return responseDto.getFailResponseDto();
                }
                return responseDto.getSuccessResponseDtoWithData(bookDto);
            }
        }


    }
}
