package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.MemberDTO;
import com.pgc.book.mapper.MemberMapper; // MemberMapper를 import
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service // Spring Service Bean으로 등록
@RequiredArgsConstructor // final 필드 생성자 주입
@Transactional(readOnly = true)
public class MemberServiceImpl implements MemberService { // MemberService 구현

    // MemberMapper를 주입받습니다.
    private final MemberMapper memberMapper;

    @Transactional
    @Override
    public MemberDTO registerMember(MemberDTO member) {
        // Mapper에게 작업을 위임합니다.
        memberMapper.insertMember(member);
        return member;
    }

    @Override
    public List<MemberDTO> getAllMembers() {
        // Mapper에게 작업을 위임합니다.
        return memberMapper.findAllMembers();
    }

    @Override
    public MemberDTO getMemberById(int memberId) {
        // Mapper에게 작업을 위임합니다.
        return memberMapper.findMemberById(memberId);
    }

    @Override
    public List<BookDTO> getBooksRentedByMemberName(String name) {
        return memberMapper.findBooksRentedByMemberName(name);
    }

    @Transactional // (쓰기 가능)
    @Override
    public boolean updateMember(MemberDTO member) {
        int affectedRows = memberMapper.updateMember(member);
        // 업데이트된 행이 1개이면 true 반환
        return affectedRows == 1;
    }
}