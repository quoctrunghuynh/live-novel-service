package com.livenovel.dev.controller;

import com.livenovel.dev.payload.ResponseDto;
import com.livenovel.dev.payload.user.request.UserUpdateRequest;
import com.livenovel.dev.payload.user.response.AuthenticationResponse;
import com.livenovel.dev.service.interf.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Object> getById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.get(id));
    }

    @PutMapping
    public ResponseEntity<ResponseDto> update(@RequestBody UserUpdateRequest userUpdateRequest) {
        return ResponseEntity.ok(userService.update(userUpdateRequest));
    }

    @DeleteMapping
    public ResponseEntity<ResponseDto> delete(@RequestBody AuthenticationResponse auth, HttpServletRequest request){
        return ResponseEntity.ok(userService.delete(auth.getToken(), request));
    }
}
