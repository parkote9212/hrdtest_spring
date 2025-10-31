package com.pgc.book.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@Builder               // [ ⭐️ 1. 빌더 추가 ⭐️ ]
@AllArgsConstructor    // [ ⭐️ 2. 빌더가 사용할 생성자 ⭐️ ]
@NoArgsConstructor
public class RentalDTO {

    private int rentalId;
    private int memberId;
    private int bookId;
    private LocalDate rentDate;
    private LocalDate returnDate;
}
