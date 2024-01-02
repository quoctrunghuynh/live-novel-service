package com.livenovel.dev.converter.abs;

import com.livenovel.dev.converter.GeneralConverter;
import com.livenovel.dev.entity.Book;
import com.livenovel.dev.payload.user.response.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
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

    public Book revertToEntity(BookDto source) {
        Book target = new Book();
        BeanUtils.copyProperties(source, target);
        return target;
    }
}
