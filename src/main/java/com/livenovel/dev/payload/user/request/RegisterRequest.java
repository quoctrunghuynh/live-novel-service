package com.livenovel.dev.payload.user.request;

import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String displayName;
}
