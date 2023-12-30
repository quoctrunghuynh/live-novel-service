package com.livenovel.dev.service.interf;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.UserUpdateRequest;
import jakarta.servlet.http.HttpServletRequest;

public interface UserService {
    Object get(Long id);
    ResponseDto update(UserUpdateRequest userUpdateRequest);
    ResponseDto delete(String token, HttpServletRequest request);
}
