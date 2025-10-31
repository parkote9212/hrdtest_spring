package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.BookRentalCountDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*; // AssertJ의 정적 import
import static org.assertj.core.api.Assertions.*;

// 1. @SpringBootTest:
//    실제 Spring Boot 애플리케이션을 '테스트 모드'로 실행합니다.
//    모든 Bean(Controller, Service, Mapper)이 메모리에 로드됩니다.
@SpringBootTest
class BookServiceTest {

    // 2. @Autowired:
    //    Spring 컨테이너에 로드된 'BookService' Bean을 여기에 주입(DI)합니다.
    @Autowired
    private BookService bookService;

    // 3. @Test:
    //    이 메서드가 JUnit이 실행해야 할 '테스트 케이스'임을 알립니다.
    @Test
    // 4. @DisplayName:
    //    테스트 실행 시 "registerBook_and_getBookById_test" 대신
    //    한글로 알기 쉬운 이름을 부여합니다.
    @DisplayName("책 등록(Register) 및 ID 조회(Get) 테스트")
    // 5. @Transactional:
    //    [ ⭐️ 매우 중요 ⭐️ ]
    //    이 테스트 메서드가 DB에 가하는 모든 변경(INSERT, UPDATE, DELETE)을
    //    테스트가 끝나자마자 즉시 '롤백(ROLLBACK)'시킵니다.
    //    즉, 이 테스트는 실제 DB를 '잠깐' 사용하지만,
    //    DB 데이터를 '절대로' 영구적으로 오염시키지 않습니다.
    @Transactional
    void registerBook_and_getBookById_test() {
        // [ 1. Given - 준비 ]
        // 테스트용 책 DTO를 만듭니다.
        BookDTO newBook = new BookDTO();
        newBook.setTitle("TDD 테스트");
        newBook.setAuthor("테스터");
        newBook.setPublisher("IT출판");
        newBook.setPrice(15000);
        newBook.setPubYear("2025");

        // [ 2. When - 실행 ]
        // (1) Service를 호출하여 책을 등록합니다. (INSERT 실행)
        BookDTO createdBook = bookService.registerBook(newBook);

        // (2) 반환된 DTO의 bookId를 사용하여 방금 등록한 책을 다시 조회합니다. (SELECT 실행)
        BookDTO foundBook = bookService.getBookById(createdBook.getBookId());

        // [ 3. Then - 검증 ]
        // JUnit의 Assertions(단언)을 사용하여 결과가 올바른지 검증합니다.

        // (검증 1) 조회된 책(foundBook)은 null이 아니어야 한다.
        assertNotNull(foundBook);

        // (검증 2) 새로 등록한 책의 제목('TDD 테스트')과
        //          조회된 책의 제목(foundBook.getTitle())은 같아야 한다.
        assertEquals("TDD 테스트", foundBook.getTitle());

        // (검증 3) 저자도 같아야 한다.
        assertEquals("테스터", foundBook.getAuthor());
    }

    // @Test: JUnit 5에게 이 메서드가 '테스트 케이스'임을 알립니다.
    //        이 어노테이션이 붙은 메서드를 테스트 실행 시 자동으로 찾아 실행합니다.
    @Test
    // @DisplayName: 테스트 실행 결과 창에 "getBooksPublishedAfter_test" 대신
    //                " (1) 2020년 이후 출판된 책 검색 TDD" 라는 한글 이름을 보여줘서
    //                어떤 테스트인지 쉽게 알 수 있게 합니다. (가독성 향상)
    @DisplayName("(1) 2020년 이후 출판된 책 검색 TDD")
    // @Transactional: [ ⭐️ 핵심 ⭐️ ]
    //    이 테스트가 DB를 사용함을 알립니다. (Service가 Mapper를 호출하므로)
    //    테스트가 시작될 때 트랜잭션을 시작하고,
    //    테스트가 '성공/실패'와 관계없이 '종료'되면,
    //    이 메서드 안에서 발생한 모든 DB 변경(INSERT, UPDATE 등)을
    //    자동으로 '롤백(ROLLBACK)'시킵니다.
    //    (결과: DB 데이터가 테스트로 인해 오염되지 않습니다.)
    @Transactional
    void getBooksPublishedAfter_test() {

        // --- [ 1. Given - 준비 단계 ] ---
        // 테스트에 필요한 환경과 변수를 설정합니다.

        // (1)번 문제('2020년 이상')를 테스트하기 위한 '입력값'을 정의합니다.
        String testYear = "2020";


        // --- [ 2. When - 실행 단계 ] ---
        // 실제 테스트 대상인 'Service 메서드'를 호출합니다.

        // bookService의 getBooksPublishedAfter 메서드에 "2020"을 파라미터로 넘겨
        // 실행하고, 그 결과를 List<BookDTO> 타입의 foundBooks 변수에 저장합니다.
        List<BookDTO> foundBooks = bookService.getBooksPublishedAfter(testYear);


        // --- [ 3. Then - 검증 단계 ] ---
        // '실행 결과(foundBooks)'가 '우리가 기대한 값'과 일치하는지 확인합니다.

        // (검증 1) "assertEquals(기대하는 값, 실제 값);"
        //    우리가 넣은 더미 데이터 기준으로 2020년 이상인 책은 3권입니다.
        //    따라서 foundBooks 리스트의 크기(size)가 3일 것이라고 단언(Assert)합니다.
        //    만약 3이 아니면(예: 2권) 테스트는 여기서 '실패'합니다.
        assertEquals(3, foundBooks.size());

        // (검증 2) "3권이 맞다면, 그 3권의 내용도 모두 2020년 이상인가?"
        //    Java 8 Stream API를 사용하여 리스트를 검사합니다.
        boolean allMatch = foundBooks.stream() // foundBooks 리스트를 스트림(Stream)으로 변환

                // .allMatch(...): 스트림의 '모든' 요소가 괄호 안의 '조건'을
                //                '전부' 만족하면 true, 하나라도 아니면 false를 반환합니다.
                .allMatch(
                        // 람다식(Lambda): 리스트의 각 요소를 'book'이라는 변수로 받아서
                        //                화살표(->) 뒤의 로직을 실행합니다.
                        book ->
                                // book 객체의 출판연도(String "2021" 등)를 가져와서
                                // Integer.parseInt()로 숫자(int 2021)로 변환한 뒤,
                                // 그 숫자가 2020보다 크거나 같은지(>=) 비교합니다.
                                Integer.parseInt(book.getPubYear()) >= 2020
                );

        // (검증 3) "assertTrue(검증할 값);"
        //    (검증 2)에서 실행한 allMatch 변수의 값은 'true'여야만 합니다.
        //    만약 'false'라면 (즉, 2019년 책이 섞여 있다면) 테스트는 '실패'합니다.
        assertTrue(allMatch);

        // (여기까지 통과하면 테스트 '성공')
    }

    @Test
    @DisplayName("(4) 도서별 대출 횟수 통계 TDD (AssertJ)")
    @Transactional
    void getBookRentalCounts_test() {
        // [ 1. Given ]
        // (더미 데이터 사용)

        // [ 2. When ]
        // 통계용 DTO 리스트를 조회합니다.
        List<BookRentalCountDTO> counts = bookService.getBookRentalCounts();

        // [ 3. Then - AssertJ로 검증 ]

        // (검증 1+2) "counts 리스트는 null이 아니며, 크기는 5여야 한다."
        assertThat(counts)
                .isNotNull()
                .hasSize(5);
        // (검증 3) '클린 코드'(bookId=5)의 대출 횟수가 0인지 정확히 확인
        // .filteredOn(...) : 리스트에서 bookId가 5인 요소만 필터링합니다.
        assertThat(counts)
                .filteredOn(dto -> dto.getBookId() == 5) // bookId 5번 필터링
                .extracting(BookRentalCountDTO::getRentalCount) // rentalCount 값 추출
                .contains(0); // 그 값이 0을 포함하는지 확인

        // (검증 4) 'JPA 프로그래밍'(bookId=1)의 대출 횟수가 2인지 확인
        assertThat(counts)
                .filteredOn(dto -> dto.getBookId() == 1) // bookId 1번 필터링
                .extracting(BookRentalCountDTO::getRentalCount) // rentalCount 값 추출
                .contains(2); // 그 값이 2를 포함하는지 확인
    }

    @Test
    @DisplayName("최고가 도서검색 TDD")
    @Transactional
    void getMostExpensiveBooks_test() {
        //1. given
        // 더미데이터 = 30000

        //2. when
        List<BookDTO> expensiveBooks = bookService.getMostExpensiveBooks();

        //3. then

        // [ 3. Then - AssertJ로 검증 (Chaining 적용) ]

        // 3개의 분리된 assertThat을 하나의 체인으로 결합합니다.
        assertThat(expensiveBooks) // "expensiveBooks 리스트는"
                .isNotNull()           // 1. null이 아니고,
                .hasSize(1)            // 2. 크기가 1이며,
                .allSatisfy(book -> {  // 3. 그 1개의 모든 요소가 아래 조건을 만족한다.

                    // 3a. 가격이 30000원이고
                    assertThat(book.getPrice()).isEqualTo(30000);
                    // 3b. 제목이 "JPA 프로그래밍"이다.
                    assertThat(book.getTitle()).isEqualTo("JPA 프로그래밍");
                });

    }

    @Test
    @DisplayName("Update 책 정보 수정 TDD")
    @Transactional
    void updateBook_test() {
        //1.Given
        BookDTO newBook = BookDTO.builder()
                .title("수정 전 제목")
                .author("수정 전 저자")
                .price(10000)
                .pubYear("2020")
                .build();
        BookDTO createdBook = bookService.registerBook(newBook);
//       새로 생성된 ID
        int bookId = createdBook.getBookId();

        BookDTO updateInfo = BookDTO.builder()
                .bookId(bookId)
                .title("수정된 제목")
                .author("수정된 저자")
                .price(12000)
                .pubYear("2020")
                .build();

        // 2. when
        boolean updateResult = bookService.updateBook(updateInfo);

        BookDTO updatedBook = bookService.getBookById(bookId);

        //3.Then
        assertThat(updateResult).isTrue();

        assertThat(updatedBook).isNotNull()
                .extracting(
                        BookDTO::getTitle,    // "수정된 제목"
                        BookDTO::getPrice,    // 12000
                        BookDTO::getAuthor,   // "수정 전 저자"
                        BookDTO::getPublisher // null
                ).containsExactly(
                        "수정된 제목",
                        12000,
                        "수정된 저자",
                        null // DTO에서 null로 보낸 Publisher가 DB에 null로 반영되었는지 검증
                );
    }

    // (BookServiceTest 클래스 내부)

    // ... (기존 테스트 메서드 5개) ...

    // [ ⭐️ 여기 추가 ⭐️ ]
    @Test
    @DisplayName("(Delete) 책 정보 삭제 TDD (AssertJ)")
    @Transactional
    // (필수) 테스트 종료 후 롤백
    void deleteBook_test() {
        // [ 1. Given - 준비 ]
        // (1) 삭제할 책을 하나 먼저 '등록'합니다.
        BookDTO newBook = BookDTO.builder()
                .title("삭제될 책")
                .author("삭제될 저자")
                .build();
        BookDTO createdBook = bookService.registerBook(newBook);
        int bookId = createdBook.getBookId(); // 새로 생성된 ID

        // (2) DB에 정말 등록되었는지 간단히 확인 (선택 사항)
        assertThat(bookService.getBookById(bookId)).isNotNull();

        // [ 2. When - 실행 ]
        // (1) Service의 deleteBook 메서드를 호출합니다.
        boolean deleteResult = bookService.deleteBook(bookId);

        // (2) DB에 정말 삭제되었는지 확인하기 위해, 다시 조회합니다.
        BookDTO foundBook = bookService.getBookById(bookId);

        // [ 3. Then - 검증 ]

        // (검증 1) deleteBook() 메서드는 true를 반환해야 합니다.
        assertThat(deleteResult).isTrue();

        // (검증 2) 삭제된 책을 다시 조회하면 'null'이어야 합니다.
        assertThat(foundBook).isNull();
    }
}