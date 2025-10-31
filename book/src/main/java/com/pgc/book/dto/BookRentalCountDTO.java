package com.pgc.book.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder               // [ ⭐️ 1. 빌더 추가 ⭐️ ]
@AllArgsConstructor    // [ ⭐️ 2. 빌더가 사용할 생성자 ⭐️ ]
@NoArgsConstructor
public class BookRentalCountDTO {

    private int bookId;
    private String title;

    private int rentalCount;
}
