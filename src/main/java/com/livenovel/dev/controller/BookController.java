package com.livenovel.dev.controller;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.BookDeleteRequest;
import com.livenovel.dev.payload.user.response.BookDto;
import com.livenovel.dev.service.interf.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final String deleteConfirm = "Corgi";

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getBookById(@PathVariable Long id){
        return ResponseEntity.ok(bookService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> saveBook(@RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.saveBook(bookDto));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateBook(@RequestBody BookDto bookDto){
        return ResponseEntity.ok(bookService.updateBook(bookDto));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteBook(@RequestBody BookDeleteRequest bookDeleteRequest){
        if(bookDeleteRequest.getConfirm().equals(deleteConfirm)){
            return ResponseEntity.ok(bookService.deleteBook(bookDeleteRequest.getId()));
        }
        return ResponseEntity.ok(ResponseDto.getInstance().getFailResponseDto());
    }
}
