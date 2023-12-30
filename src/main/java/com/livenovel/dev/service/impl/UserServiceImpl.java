package com.livenovel.dev.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.livenovel.dev.configuration.security.JwtAuthenticationFilter;
import com.livenovel.dev.configuration.security.JwtService;
import com.livenovel.dev.entity.User;
import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.UserUpdateRequest;
import com.livenovel.dev.payload.user.response.UserDto;
import com.livenovel.dev.repository.UserRepository;
import com.livenovel.dev.repository.redis.UserDtoRedisRepository;
import com.livenovel.dev.service.interf.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final UserRepository userRepository;
    private final UserDtoRedisRepository redisRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public Object get(Long id) {
        Object userInCache = redisRepository.findUserDtoWithId(id);
        if (userInCache == null) {
            Optional<User> userData = userRepository.findUserByIdAndIsDeletedIsFalse(id);
            if (userData.isEmpty()) {
                return ResponseDto.builder()
                        .status("200")
                        .message("User not found! Please try again later.")
                        .data(false)
                        .build();
            } else {
                UserDto userDtoInRepository = userData.map(
                        user -> UserDto.builder()
                                .id(user.getId())
                                .displayName(user.getDisplayName())
                                .createdAt(user.getCreatedAt())
                                .build()).orElse(null);
                redisRepository.saveUserDto(userDtoInRepository);
                return userDtoInRepository;
            }
        }
        return objectMapper.convertValue(userInCache, UserDto.class);
    }

    @Override
    public ResponseDto update(UserUpdateRequest userUpdateRequest) {
        User user = userRepository
                .findUserByIdAndIsDeletedIsFalse(userUpdateRequest.getId())
                .stream()
                .findFirst()
                .orElse(null);
        if (user != null) {
            user.setDisplayName(userUpdateRequest.getDisplayName());
            userRepository.save(user);
            redisRepository.updateUserDtoByUser(user);
            return ResponseDto.builder()
                    .status("200")
                    .message("User updated successfully!")
                    .data(true)
                    .build();
        }
        return ResponseDto.builder()
                .status("400")
                .message("User updated failed")
                .data(false)
                .build();
    }

    @Override
    public ResponseDto delete(String token, HttpServletRequest request) {
        String jwt = jwtAuthenticationFilter.getJwtFromRequest(request);
        if (jwt.equals(token)) {
            String username = jwtService.extractUsername(token);
            User user = userRepository.findUserByUsername(username).orElse(null);
            if (user != null) {
                user.setIsDeleted(true);
                userRepository.save(user);
                redisRepository.deleteUserDtoByUser(user);
                return ResponseDto.builder()
                        .status("200")
                        .message("User deleted successfully!")
                        .data(true)
                        .build();
            }
        }
        return ResponseDto.builder()
                .status("400")
                .message("User not found or has been deleted!")
                .data(false)
                .build();
    }
}
