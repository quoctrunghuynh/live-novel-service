package com.livenovel.dev.service.impl;

import com.livenovel.dev.builder.ResponseDtoBuilder;
import com.livenovel.dev.converter.abs.BookConverter;
import com.livenovel.dev.entity.Book;
import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.book.BookDto;
import com.livenovel.dev.repository.BookRepository;
import com.livenovel.dev.repository.UserRepository;
import com.livenovel.dev.repository.redis.BookDtoRedisRepository;
import com.livenovel.dev.service.interf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookDtoRedisRepository redisRepository;
    private final ResponseDtoBuilder responseDtoBuilder;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final BookConverter bookConverter;

    @Override
    public ResponseDto getById(Long id) {
        Object bookInCache = redisRepository.findBookDtoWithId(id);
        if (bookInCache == null) {
            Optional<Book> book = bookRepository.findBookByIdAndIsDeletedIsFalse(id);
            if (book.isEmpty()) {
                return responseDtoBuilder.build("Failed", false, HttpStatus.BAD_REQUEST);
            } else {
                BookDto bookDto = bookConverter.convert(book);
                redisRepository.saveBookDto(bookDto);
                if (bookDto == null) {
                    return responseDtoBuilder.build("Failed", false, HttpStatus.BAD_REQUEST);
                }
                return responseDtoBuilder.build("Success", bookDto, HttpStatus.OK);
            }
        }
        return responseDtoBuilder.build("Success", bookInCache, HttpStatus.OK);
    }

    @Override
    public ResponseDto saveBook(BookDto bookDto) {
        Book book = bookConverter.revert(bookDto);
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdateAt(LocalDateTime.now());
        book.setIsDeleted(false);
        book.setUser(userRepository.findById(bookDto.getUserId()).orElse(null));
        if (book.getUser() != null) {
            bookRepository.save(book);
            BookDto savedBookDto = bookConverter.convert(book);
            redisRepository.saveBookDto(savedBookDto);
            return responseDtoBuilder.build("Success", book, HttpStatus.OK);
        } else {
            return responseDtoBuilder.build("Failed", false, HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseDto updateBook(BookDto bookDto) {
        Book book = bookRepository
                .findBookByIdAndIsDeletedIsFalse(bookDto.getId()).stream()
                .findFirst()
                .orElse(null);
        if (book != null) {
            book.setTitle(bookDto.getTitle());
            book.setContent(bookDto.getContent());
            book.setUpdateAt(LocalDateTime.now());
            book.setCreatedAt(bookDto.getCreatedAt());
            bookRepository.save(book);
            redisRepository.saveBookDto(bookDto);
            return responseDtoBuilder.build("Success", true, HttpStatus.OK);
        }
        return responseDtoBuilder.build("Failed", false, HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseDto deleteBook(Long id) {
        Book book = bookRepository
                .findBookByIdAndIsDeletedIsFalse(id).stream()
                .findFirst()
                .orElse(null);
        if (book != null) {
            book.setIsDeleted(true);
            bookRepository.save(book);
            redisRepository.deleteBookDtoByBook(book);
            return responseDtoBuilder.build("Success", true, HttpStatus.OK);
        }
        return responseDtoBuilder.build("Failed", false, HttpStatus.BAD_REQUEST);
    }
}