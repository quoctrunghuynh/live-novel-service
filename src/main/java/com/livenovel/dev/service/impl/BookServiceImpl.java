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

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookDtoRedisRepository redisRepository;
    private final BookConverter bookConverter;

    @Override
    public ResponseDto getById(Long id) {
        Object bookInCache = redisRepository.findBookDtoWithId(id);
        if (bookInCache == null) {
            Optional<Book> book = bookRepository.findBookByIdAndIsDeletedIsFalse(id);
            if (book.isEmpty()) {
                return ResponseDto.getInstance().getFailResponseDto();
            } else {
                BookDto bookDto = bookConverter.convert(book);
                redisRepository.saveBookDto(bookDto);
                if (bookDto == null) {
                    return ResponseDto.getInstance().getFailResponseDto();
                }
                return ResponseDto.getInstance().getSuccessResponseDtoWithData(bookDto);
            }
        }
        return ResponseDto.getInstance().getSuccessResponseDtoWithData(bookInCache);
    }

    @Override
    public ResponseDto saveBook(BookDto bookDto) {
        bookRepository.save(bookConverter.revertToEntity(bookDto));
        redisRepository.saveBookDto(bookDto);
        return ResponseDto.getInstance().getSuccessResponseDtoWithData(bookDto);
    }

    @Override
    public ResponseDto updateBook(BookDto bookDto) {
        Book book = bookRepository
                .findBookByIdAndIsDeletedIsFalse(bookDto.getId()).stream()
                .findFirst()
                .orElse(null);
        if(book != null){
            book.setTitle(bookDto.getTitle());
            book.setContent(bookDto.getContent());
            book.setUpdateAt(LocalDateTime.now());
            book.setCreatedAt(bookDto.getCreatedAt());
            bookRepository.save(book);
            redisRepository.saveBookDto(bookDto);
            return ResponseDto.getInstance().getSuccessResponseDtoWithData(bookDto);
        }
        return ResponseDto.getInstance().getFailResponseDto();
    }

    @Override
    public ResponseDto deleteBook(Long id) {
        Book book = bookRepository
                .findBookByIdAndIsDeletedIsFalse(id).stream()
                .findFirst()
                .orElse(null);
        if(book != null){
            book.setIsDeleted(true);
            bookRepository.save(book);
            redisRepository.deleteBookDtoByBook(book);
            return ResponseDto.getInstance().getSuccessResponseDtoWithData(true);
        }
        return ResponseDto.getInstance().getFailResponseDto();
    }


}