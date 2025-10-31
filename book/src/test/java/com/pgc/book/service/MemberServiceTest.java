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
@Transactional // DB를 사용하므로 모든 테스트는 롤백됩니다.
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    @DisplayName("(2) '홍길동' 회원이 대출한 도서 목록 TDD")
    void getBooksRentedByMemberName_test() {
        // [ 1. Given - 준비 ]
        // (더미 데이터 SQL이 이미 실행되었다고 가정합니다.)
        String testName = "홍길동";

        // [ 2. When - 실행 ]
        // '홍길동'으로 Service 메서드를 호출합니다.
        List<BookDTO> foundBooks = memberService.getBooksRentedByMemberName(testName);

        // [ 3. Then - 검증 ]
        // (검증 1) 리스트는 null이 아니어야 합니다.
        assertNotNull(foundBooks);

        // (검증 2) 더미 데이터 기준으로 '홍길동'은 2권을 빌렸습니다.
        assertEquals(2, foundBooks.size());

        // (검증 3) 2권의 책 제목이 'JPA 프로그래밍'과 'SQL 기초'가 맞는지 검증
        // Stream을 사용하여 제목(title)만 뽑아내서 리스트로 만듭니다.
        List<String> titles = foundBooks.stream()
                .map(BookDTO::getTitle) // book -> book.getTitle()
                .toList();

        // (검증 4) 이 리스트에 "JPA 프로그래밍"이 포함되어 있는지 확인
        assertTrue(titles.contains("JPA 프로그래밍"));
        // (검증 5) 이 리스트에 "SQL 기초"가 포함되어 있는지 확인
        assertTrue(titles.contains("SQL 기초"));
    }
}