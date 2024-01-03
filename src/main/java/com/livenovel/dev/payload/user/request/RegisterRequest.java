package com.livenovel.dev.payload.user.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String displayName;
}
