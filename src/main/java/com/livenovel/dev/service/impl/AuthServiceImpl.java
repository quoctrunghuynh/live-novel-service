package com.livenovel.dev.service.impl;

import com.livenovel.dev.configuration.security.JwtService;
import com.livenovel.dev.entity.Role;
import com.livenovel.dev.entity.User;
import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.AuthenticateRequest;
import com.livenovel.dev.payload.user.request.RegisterRequest;
import com.livenovel.dev.payload.user.response.AuthenticationResponse;
import com.livenovel.dev.repository.UserRepository;
import com.livenovel.dev.service.interf.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public ResponseDto register(RegisterRequest registerRequest) {
        var user = User.builder()
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .displayName(registerRequest.getDisplayName())
                .createdAt(LocalDateTime.now())
                .isDeleted(false)
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return ResponseDto.builder()
                .status("200")
                .message("User register success")
                .data(AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build())
                .build();

    }

    @Override
    public ResponseDto authenticate(AuthenticateRequest authenticateRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticateRequest.getUsername(),
                        authenticateRequest.getPassword()
                )
        );
        var user = userRepository.findUserByUsernameAndIsDeletedIsFalse(authenticateRequest.getUsername())
                .orElseThrow(
                        () -> new UsernameNotFoundException("Username not exist or deleted")
                );
        if (user == null) {
            return ResponseDto.builder()
                    .status("404")
                    .message("Get user's token failed, user not exist or deleted")
                    .data(null)
                    .build();
        }
        var jwtToken = jwtService.generateToken(user);
        return ResponseDto.builder()
                .status("200")
                .message("Get user's token success")
                .data(AuthenticationResponse.builder()
                        .token(jwtToken)
                        .build())
                .build();
    }
}
