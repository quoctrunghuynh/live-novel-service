package com.livenovel.dev.payload.novel.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NovelDeleteRequest {
    private Long id;
    private String confirm;
}
