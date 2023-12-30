package com.livenovel.dev.repository;

import com.livenovel.dev.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIdAndIsDeletedIsFalse(Long id);
}
