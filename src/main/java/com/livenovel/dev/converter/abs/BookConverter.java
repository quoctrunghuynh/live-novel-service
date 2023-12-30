package com.livenovel.dev.converter.abs;

import com.livenovel.dev.converter.GeneralConverter;
import com.livenovel.dev.entity.Book;
import com.livenovel.dev.payload.user.response.BookDto;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public abstract class BookConverter implements GeneralConverter<Optional<Book>, BookDto> {
    @Override
    public BookDto convert(Optional<Book> source) {
        BookDto target = new BookDto();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    @Override
    public List<BookDto> convert(List<Optional<Book>> sources) {
        return sources.stream().map(this::convert).toList();
    }
}
