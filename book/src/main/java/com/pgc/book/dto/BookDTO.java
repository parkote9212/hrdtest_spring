package com.pgc.book.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter
@Setter
@ToString
@Builder               // [ ⭐️ 1. 빌더 추가 ⭐️ ]
@AllArgsConstructor    // [ ⭐️ 2. 빌더가 사용할 생성자 ⭐️ ]
@NoArgsConstructor
public class BookDTO {

    private int bookId;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Size(max = 100)
    private String author;

    @Size(max = 100)
    private String publisher;

    @NotNull
    @Min(value = 0)
    @Max(value = 1000000)
    private Integer price;

    @NotBlank
    @Pattern(regexp = "^(19|20)\\d{2}$")
    private String pubYear;
}
