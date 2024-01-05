package com.livenovel.dev.repository.redis;

import com.livenovel.dev.entity.Novel;
import com.livenovel.dev.payload.novel.NovelDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository("NovelDtoRedis")
@RequiredArgsConstructor
public class NovelDtoRedisRepository {
    public final static String KEY = "NovelDto";

    private final RedisTemplate<String, Object> template;

    public Object findNovelDtoWithId(Long id) {
        return template.opsForHash().get(KEY, id);
    }

    public void saveNovelDto(NovelDto novelDto) {
        template.opsForHash().put(KEY, novelDto.getId(), novelDto);
    }

    public void updateBookDtoByBook(Novel novel) {
        NovelDto novelDto = new NovelDto();
        BeanUtils.copyProperties(novel, novelDto);
        template.opsForHash().put(KEY, novelDto.getId(), novelDto);
    }

    public void deleteNovelDtoByBook(Novel user) {
        NovelDto novelDto = new NovelDto();
        BeanUtils.copyProperties(user, novelDto);
        template.opsForHash().delete(KEY, novelDto.getId());
    }
}
