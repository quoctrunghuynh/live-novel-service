package com.livenovel.dev.controller;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.novel.NovelDto;
import com.livenovel.dev.payload.novel.request.NovelDeleteRequest;
import com.livenovel.dev.service.interf.NovelService;
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
@RequestMapping("/api/novels")
public class NovelController {
    private final NovelService novelService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getNovelById(@PathVariable Long id){
        return ResponseEntity.ok(novelService.getById(id));
    }

    @PostMapping
    public ResponseEntity<ResponseDto> saveNovel(@RequestBody NovelDto novelDto){
        return ResponseEntity.ok(novelService.saveBook(novelDto));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> updateNovel(@RequestBody NovelDto novelDto){
        return ResponseEntity.ok(novelService.updateBook(novelDto));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> deleteNovel(@RequestBody NovelDeleteRequest novelDeleteRequest){
        return ResponseEntity.ok(novelService.deleteBook(novelDeleteRequest));
    }
}
