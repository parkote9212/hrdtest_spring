package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // DB를 사용하므로 롤백
class RentalServiceTest {

    @Autowired
    private RentalService rentalService;

    @Test
    @DisplayName("(3) 미반납(Unreturned) 도서 목록 TDD")
    void getUnreturnedBooks_test() {
        // [ 1. Given - 준비 ]
        // (더미 데이터 SQL이 이미 실행되었다고 가정합니다.)

        // [ 2. When - 실행 ]
        // 미반납 도서 목록을 조회하는 Service 메서드를 호출합니다.
        List<BookDTO> unreturnedBooks = rentalService.getUnreturnedBooks();

        // [ 3. Then - 검증 ]
        // (검증 1) 리스트는 null이 아니어야 합니다.
        assertNotNull(unreturnedBooks);

        // (검증 2) 더미 데이터 기준으로 미반납 도서는 2권입니다.
        assertEquals(2, unreturnedBooks.size());

        // (검증 3) 2권의 책 제목이 '스프링 부트 입문'과 '알고리즘 A'가 맞는지 검증
        List<String> titles = unreturnedBooks.stream()
                .map(BookDTO::getTitle) // 메서드 참조
                .toList();

        // (검증 4) "스프링 부트 입문"이 포함되어 있는지 확인
        assertTrue(titles.contains("스프링 부트 입문"));
        // (검증 5) "알고리즘 A"가 포함되어 있는지 확인
        assertTrue(titles.contains("알고리즘 A"));
    }
}