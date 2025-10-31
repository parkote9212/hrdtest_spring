package com.pgc.book.controller;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.BookRentalCountDTO;
import com.pgc.book.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/books")
public class BookRestController {

    private final BookService bookService;

    /**
     * [POST] /api/books
     * 책 등록 API
     *
     * @param book DTO
     * @return ResponseEntity<BookDTO> (201 Created)
     */
    @PostMapping
    public ResponseEntity<BookDTO> registerBook(@Valid @RequestBody BookDTO book) {
        BookDTO createdBook = bookService.registerBook(book);
        // 201 Created 상태 코드와 생성된 책의 정보를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    /**
     * [GET] /api/books
     * 모든 책 조회 API
     *
     * @return ResponseEntity<List<BookDTO>> (200 OK)
     */
    @GetMapping
    public ResponseEntity<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        // 200 OK 상태 코드와 책 목록을 반환
        return ResponseEntity.ok(books); // .ok() == .status(HttpStatus.OK)
    }

    /**
     * [GET] /api/books/{bookId}
     * 특정 ID의 책 한 권 조회 API
     *
     * @param bookId (URL 경로의 변수)
     * @return ResponseEntity<BookDTO> (200 OK 또는 404 Not Found)
     */
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable int bookId) {
        BookDTO book = bookService.getBookById(bookId);

        if (book == null) {
            // 조회 결과가 없으면 404 Not Found 반환
            return ResponseEntity.notFound().build();
        } else {
            // 조회 결과가 있으면 200 OK와 DTO 반환
            return ResponseEntity.ok(book);
        }
    }

    /**
     * [PUT] /api/books/{bookId}
     * (Update) 책 정보 수정 API
     * @param bookId (URL 경로의 변수)
     * @param book (요청 Body의 JSON 데이터)
     * @return ResponseEntity<BookDTO> (200 OK 또는 404 Not Found)
     */
    @PutMapping("/{bookId}")
    public ResponseEntity<BookDTO> updateBook(
            @PathVariable int bookId,
            @Valid @RequestBody BookDTO book){
        book.setBookId(bookId);
        boolean isSuccess = bookService.updateBook(book);

        if(isSuccess){
            return ResponseEntity.ok(book);
        }else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * [DELETE] /api/books/{bookId}
     * (Delete) 책 정보 삭제 API
     * @param bookId (URL 경로의 변수)
     * @return ResponseEntity<Void> (204 No Content 또는 404 Not Found)
     */
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable int bookId){

        boolean isSuccess = bookService.deleteBook(bookId);

        if(isSuccess){
            return ResponseEntity.noContent().build();
        } else  {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * [GET] /api/books/search
     * 특정 연도(Query Parameter) 이후 출판된 도서 검색 API
     *
     * @param year (URL 쿼리 파라미터)
     * @return ResponseEntity<List<BookDTO>> (200 OK)
     */
    @GetMapping("/search")
    public ResponseEntity<List<BookDTO>> searchBooksByYear(@RequestParam("year") String year) {
        List<BookDTO> books = bookService.getBooksPublishedAfter(year);
        // (검색 결과가 없어도 404가 아니라, 빈 리스트[]를 200 OK로 반환하는 것이 맞습니다)
        return ResponseEntity.ok(books);
    }

    @GetMapping("/stats/rental-counts")
    public ResponseEntity<List<BookRentalCountDTO>> getBookRentalCounts() {
        List<BookRentalCountDTO> books = bookService.getBookRentalCounts();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/stats/most-expensive")
    public ResponseEntity<List<BookDTO>> getMostExpensiveBooks(){
        return ResponseEntity.ok(bookService.getMostExpensiveBooks());
    }
}
