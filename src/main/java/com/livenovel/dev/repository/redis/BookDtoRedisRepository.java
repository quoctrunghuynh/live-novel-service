package com.livenovel.dev.repository.redis;

import com.livenovel.dev.entity.Book;
import com.livenovel.dev.payload.user.response.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BookDtoRedisRepository {
    public final static String KEY = "BookDto";

    private final RedisTemplate<String, Object> template;

    public Object findBookDtoWithId(Long id) {
        return template.opsForHash().get(KEY, id);
    }

    public void saveBookDto(BookDto bookDto) {
        template.opsForHash().put(KEY, bookDto.getId(), bookDto);
    }

    public void updateBookDtoByBook(Book book) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(book, bookDto);
        template.opsForHash().put(KEY, bookDto.getId(), bookDto);
    }

    public void deleteBookDtoByBook(Book user) {
        BookDto bookDto = new BookDto();
        BeanUtils.copyProperties(user, bookDto);
        template.opsForHash().delete(KEY, bookDto.getId());
    }
}
