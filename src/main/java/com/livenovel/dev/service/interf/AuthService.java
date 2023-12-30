package com.livenovel.dev.service.interf;


import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.AuthenticateRequest;
import com.livenovel.dev.payload.user.request.RegisterRequest;

public interface AuthService {
    ResponseDto register(RegisterRequest userDto);

    ResponseDto authenticate(AuthenticateRequest userDto);
}
