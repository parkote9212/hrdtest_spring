package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.BookRentalCountDTO;

import java.util.List;

public interface BookService {

    BookDTO registerBook(BookDTO book);

    List<BookDTO> getAllBooks();

    BookDTO getBookById(int bookId);

    List<BookDTO> getBooksPublishedAfter(String year);

    List<BookRentalCountDTO> getBookRentalCounts();

    List<BookDTO> getMostExpensiveBooks();

    boolean updateBook(BookDTO book);

    boolean deleteBook(int bookId);
}
