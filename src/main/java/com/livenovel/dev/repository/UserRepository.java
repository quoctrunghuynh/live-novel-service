package com.livenovel.dev.repository;

import com.livenovel.dev.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByUsernameAndIsDeletedIsFalse(String username);

    Optional<User> findUserByIdAndIsDeletedIsFalse(Long id);
}
