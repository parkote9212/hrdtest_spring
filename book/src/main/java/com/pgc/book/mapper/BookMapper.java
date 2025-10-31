package com.pgc.book.mapper;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.BookRentalCountDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    void insertBook(BookDTO book);

    List<BookDTO> findAllBooks();

    BookDTO findBookById(int bookId);

    List<BookDTO> findBooksPublishedAfter(String year);

    List<BookRentalCountDTO> getBookRentalCounts();

    List<BookDTO> findMostExpensiveBooks();

    int updateBook(BookDTO book);

    int deleteBook(int bookId);
}
