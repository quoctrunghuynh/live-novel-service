package com.livenovel.dev.payload.book.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookDeleteRequest {
    private Long id;
    private String confirm;
}
