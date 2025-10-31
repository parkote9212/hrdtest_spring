package com.pgc.book.mapper;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.RentalDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RentalMapper {

    void insertRental(RentalDTO rental);

    List<RentalDTO> findAllRentals();

    RentalDTO findRentalById(int rentalId);

    List<BookDTO> findUnreturnedBooks();

}
