package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.BookRentalCountDTO;
import com.pgc.book.mapper.BookMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BookServiceImpl implements BookService{

    private final BookMapper bookMapper;

    @Transactional
    @Override
    public BookDTO registerBook(BookDTO book){
        bookMapper.insertBook(book);
        return book;
    }

    @Override
    public List<BookDTO> getAllBooks() {
        return bookMapper.findAllBooks();
    }

    @Override
    public BookDTO getBookById(int bookId) {
        return bookMapper.findBookById(bookId);
    }

    @Override
    public List<BookDTO> getBooksPublishedAfter(String year) {
        return bookMapper.findBooksPublishedAfter(year);
    }

    @Override
    public List<BookRentalCountDTO> getBookRentalCounts() {
        return bookMapper.getBookRentalCounts();
    }

    @Override
    public List<BookDTO> getMostExpensiveBooks() {
        return bookMapper.findMostExpensiveBooks();
    }

    @Transactional
    @Override
    public boolean updateBook(BookDTO book) {
        int affectedRows = bookMapper.updateBook(book);
        return affectedRows == 1;
    }

    @Transactional
    @Override
    public boolean deleteBook(int bookId) {
        int affectedRows = bookMapper.deleteBook(bookId);
        return affectedRows == 1;
    }
}
