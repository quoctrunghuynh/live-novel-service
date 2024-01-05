package com.livenovel.dev.payload.novel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash("NovelDto")
public class NovelDto implements Serializable {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime updateAt;
    private LocalDateTime createdAt;
    private Long userId;
}
