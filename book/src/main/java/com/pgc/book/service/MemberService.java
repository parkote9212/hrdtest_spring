package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.MemberDTO;
import java.util.List;

public interface MemberService {

    // 1. 회원 등록
    MemberDTO registerMember(MemberDTO member);

    // 2. 모든 회원 조회
    List<MemberDTO> getAllMembers();

    // 3. ID로 회원 한 명 조회
    MemberDTO getMemberById(int memberId);

    List<BookDTO> getBooksRentedByMemberName(String name);
}