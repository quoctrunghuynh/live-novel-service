package com.livenovel.dev.repository;

import com.livenovel.dev.entity.Book;
import com.livenovel.dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIdAndIsDeletedIsFalse(Long id);
    Optional<Book> findFirstByUserOrderByCreatedAtDesc(User user);
}
