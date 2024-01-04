package com.livenovel.dev.converter.abs;

import com.livenovel.dev.converter.GeneralConverter;
import com.livenovel.dev.entity.Book;
import com.livenovel.dev.entity.User;
import com.livenovel.dev.payload.book.BookDto;
import com.livenovel.dev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookConverter implements GeneralConverter<Book, BookDto> {

    private final UserRepository userRepository;

    @Override
    public BookDto convert(Book source) {
        BookDto target = new BookDto();
        BeanUtils.copyProperties(source, target);
        target.setUpdateAt(source.getUpdateAt());
        target.setCreatedAt(source.getCreatedAt());
        target.setUserId(source.getUser().getId());
        return target;
    }

    @Override
    public Book revert(BookDto target) {
        Book source = new Book();
        BeanUtils.copyProperties(target, source);
        return source;
    }

    @Override
    public List<BookDto> convert(List<Book> sources) {
        return sources.stream().map(this::convert).toList();
    }

    @Override
    public List<Book> revert(List<BookDto> targets) {
        return targets.stream().map(this::revert).toList();
    }
}
