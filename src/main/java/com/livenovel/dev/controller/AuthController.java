package com.livenovel.dev.controller;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.AuthenticateRequest;
import com.livenovel.dev.payload.user.request.RegisterRequest;
import com.livenovel.dev.service.interf.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> register (@RequestBody RegisterRequest registerRequest){
            return ResponseEntity.ok(authService.register(registerRequest));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<ResponseDto> authenticate (@RequestBody AuthenticateRequest authenticateRequest){
        return ResponseEntity.ok(authService.authenticate(authenticateRequest));
    }

}
