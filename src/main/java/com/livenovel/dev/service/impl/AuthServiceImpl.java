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
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JwtService jwtService;

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
        var jwtToken = jwtService.generateToken(user);
        if (jwtToken == null) {
            return ResponseDto.getDefaultFailed(false);
        } else {
            userRepository.save(user);
            return ResponseDto.getDefaultSucceed(jwtToken);
        }
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
            return ResponseDto.getDefaultFailed(false);
        }
        var jwtToken = jwtService.generateToken(user);
        return ResponseDto.getDefaultSucceed(
                AuthenticationResponse.builder()
                .token(jwtToken)
                .build()
        );
    }
}
