package com.pgc.book.controller;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.RentalDTO;
import com.pgc.book.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;       // [ ⭐️ import 추가 ⭐️ ]
import org.springframework.http.ResponseEntity; // [ ⭐️ import 추가 ⭐️ ]
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rentals")
public class RentalRestController {

    private final RentalService rentalService;

    /**
     * [POST] /api/rentals
     * 대출 등록 API
     * @param rental DTO
     * @return ResponseEntity<RentalDTO> (201 Created)
     */
    @PostMapping
    public ResponseEntity<RentalDTO> registerRental(@RequestBody RentalDTO rental) {
        // 201 Created 상태 코드와 생성된 대출 정보를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(rentalService.registerRental(rental));
    }

    /**
     * [GET] /api/rentals
     * 모든 대출 내역 조회 API
     * @return ResponseEntity<List<RentalDTO>> (200 OK)
     */
    @GetMapping
    public ResponseEntity<List<RentalDTO>> getAllRentals() {
        // 200 OK 상태 코드와 대출 목록을 반환
        return ResponseEntity.ok(rentalService.getAllRentals());
    }

    /**
     * [GET] /api/rentals/{rentalId}
     * 특정 ID의 대출 내역 조회 API
     * @param rentalId (URL 경로의 변수)
     * @return ResponseEntity<RentalDTO> (200 OK 또는 404 Not Found)
     */
    @GetMapping("/{rentalId}")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable int rentalId) {
        RentalDTO rental = rentalService.getRentalById(rentalId);

        // (삼항 연산자를 사용한 널 체크)
        return (rental == null)
                ? ResponseEntity.notFound().build()   // 404 Not Found
                : ResponseEntity.ok(rental);            // 200 OK
    }

    /**
     * [GET] /api/rentals/unreturned
     * 미반납 도서 목록 조회 API
     * @return ResponseEntity<List<BookDTO>> (200 OK)
     */
    @GetMapping("/unreturned")
    public ResponseEntity<List<BookDTO>> getUnreturnedBooks(){
        // (검색 결과가 없어도 빈 리스트[]를 200 OK로 반환)
        return ResponseEntity.ok(rentalService.getUnreturnedBooks());
    }
}