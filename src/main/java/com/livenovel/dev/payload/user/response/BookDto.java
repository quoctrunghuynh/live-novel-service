package com.livenovel.dev.payload.user.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("BookDto")
public class BookDto {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updateAt;
    private LocalDateTime createdAt;
}
