package com.livenovel.dev.repository;

import com.livenovel.dev.entity.Novel;
import com.livenovel.dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NovelRepository extends JpaRepository<Novel, Long> {
    Optional<Novel> findNovelByIdAndIsDeletedIsFalse(Long id);
    Optional<Novel> findFirstByUserOrderByCreatedAtDesc(User user);
}
