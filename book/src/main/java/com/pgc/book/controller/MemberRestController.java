package com.pgc.book.controller;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.MemberDTO;
import com.pgc.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;       // [ ⭐️ import 추가 ⭐️ ]
import org.springframework.http.ResponseEntity; // [ ⭐️ import 추가 ⭐️ ]
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberRestController {

    private final MemberService memberService;

    /**
     * [POST] /api/members
     * 멤버 등록 API
     * @param member DTO
     * @return ResponseEntity<MemberDTO> (201 Created)
     */
    @PostMapping
    public ResponseEntity<MemberDTO> registerMember(@RequestBody MemberDTO member){
        // 201 Created 상태 코드와 생성된 회원 정보를 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.registerMember(member));
    }

    /**
     * [GET] /api/members
     * @return ResponseEntity<List<MemberDTO>> (200 OK)
     */
    @GetMapping
    public ResponseEntity<List<MemberDTO>> getAllMembers(){
        // 200 OK 상태 코드와 회원 목록을 반환
        return ResponseEntity.ok(memberService.getAllMembers());
    }

    /**
     * [GET] /api/members/{memberId}
     * 특정 ID의 멤버 조회 API
     * @param memberId (URL 경로의 변수)
     * @return ResponseEntity<MemberDTO> (200 OK 또는 404 Not Found)
     */
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDTO> getMemberById(@PathVariable int memberId){
        MemberDTO member = memberService.getMemberById(memberId);

        // (삼항 연산자를 사용한 널 체크)
        return (member == null)
                ? ResponseEntity.notFound().build()   // 404 Not Found
                : ResponseEntity.ok(member);            // 200 OK
    }

    /**
     * [GET] /api/members/search/books?name=...
     * 특정 이름의 회원이 대출한 도서 목록 조회 API
     * @param name (URL 쿼리 파라미터)
     * @return ResponseEntity<List<BookDTO>> (200 OK)
     */
    @GetMapping("/search/books")
    public ResponseEntity<List<BookDTO>> getBooksRentedByMember(@RequestParam("name") String name) {
        // (검색 결과가 없어도 빈 리스트[]를 200 OK로 반환)
        return ResponseEntity.ok(memberService.getBooksRentedByMemberName(name));
    }

}