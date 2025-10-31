package com.pgc.book.mapper;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MemberMapper {

    void insertMember(MemberDTO member);

    List<MemberDTO> findAllMembers();

    MemberDTO findMemberById(int memberId);

    List<BookDTO> findBooksRentedByMemberName(String name);

    int updateMember(MemberDTO member);
}
