package com.livenovel.dev.controller;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.service.interf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private BookService bookService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getBookById(@PathVariable Long id){

    }
}
