package com.livenovel.dev.service.impl;

import com.livenovel.dev.converter.abs.NovelConverter;
import com.livenovel.dev.entity.Novel;
import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.novel.NovelDto;
import com.livenovel.dev.payload.novel.request.NovelDeleteRequest;
import com.livenovel.dev.repository.NovelRepository;
import com.livenovel.dev.repository.UserRepository;
import com.livenovel.dev.repository.redis.NovelDtoRedisRepository;
import com.livenovel.dev.service.interf.NovelService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class NovelServiceImpl implements NovelService {

    @Value("${app.delete_confirm}")
    private String DELETE_CONFIRM;

    private final NovelDtoRedisRepository redisRepository;
    private final NovelRepository novelRepository;
    private final UserRepository userRepository;
    private final NovelConverter novelConverter;

    @Override
    public ResponseDto getById(Long id) {
        Object novelInCache = redisRepository.findNovelDtoWithId(id);
        if (novelInCache == null) {
            Novel novel = novelRepository.findNovelByIdAndIsDeletedIsFalse(id).orElse(null);
            if (novel == null) {
                return ResponseDto.getDefaultFailed(false);
            } else {
                NovelDto novelDto = novelConverter.convert(novel);
                redisRepository.saveNovelDto(novelDto);
                return ResponseDto.getDefaultSucceed(novelDto);
            }
        }
        return ResponseDto.getDefaultSucceed(novelInCache);
    }

    @Override
    public ResponseDto saveBook(NovelDto novelDto) {
        Novel novel = novelConverter.revert(novelDto);
        novel.setCreatedAt(LocalDateTime.now());
        novel.setUpdateAt(LocalDateTime.now());
        novel.setIsDeleted(false);
        novel.setUser(userRepository.findUserByIdAndIsDeletedIsFalse(novelDto.getUserId()).orElse(null));
        if (novel.getUser() != null) {
            novelRepository.save(novel);
            NovelDto savedNovelDto = novelConverter.convert(novel);
            redisRepository.saveNovelDto(savedNovelDto);
            return ResponseDto.getDefaultSucceed(savedNovelDto);
        } else {
            return ResponseDto.getDefaultFailed(false);
        }
    }

    @Override
    public ResponseDto updateBook(NovelDto novelDto) {
        Novel novel = novelRepository.findNovelByIdAndIsDeletedIsFalse(novelDto.getId()).orElse(null);
        if (novel != null) {
            novel.setTitle(novelDto.getTitle());
            novel.setContent(novelDto.getContent());
            novel.setUpdateAt(LocalDateTime.now());
            NovelDto edittedNovelDto = novelConverter.convert(novel);
            novelRepository.save(novel);
            redisRepository.saveNovelDto(edittedNovelDto);
            return ResponseDto.getDefaultSucceed(edittedNovelDto);
        }
        return ResponseDto.getDefaultFailed(false);
    }

    @Override
    public ResponseDto deleteBook(NovelDeleteRequest novelDeleteRequest) {
        if (novelDeleteRequest.getConfirm().equals(DELETE_CONFIRM)) {
            Novel novel = novelRepository.findNovelByIdAndIsDeletedIsFalse
                    (novelDeleteRequest.getId())
                    .orElse(null);
            if (novel != null) {
                novel.setIsDeleted(true);
                novelRepository.save(novel);
                redisRepository.deleteNovelDtoByBook(novel);
                return ResponseDto.getDefaultSucceed(true);
            }
        }
        return ResponseDto.getDefaultFailed(false);
    }
}