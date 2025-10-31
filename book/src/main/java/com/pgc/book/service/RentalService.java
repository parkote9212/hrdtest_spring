package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.RentalDTO;

import java.util.List;

public interface RentalService {

    RentalDTO registerRental(RentalDTO rental);

    List<RentalDTO> getAllRentals();

    RentalDTO getRentalById(int rentalId);

    List<BookDTO> getUnreturnedBooks();
}
