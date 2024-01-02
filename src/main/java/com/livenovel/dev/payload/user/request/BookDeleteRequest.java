package com.livenovel.dev.payload.user.request;

import com.livenovel.dev.payload.user.response.BookDto;
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
