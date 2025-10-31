package com.pgc.book.service;

import com.pgc.book.dto.BookDTO;
import com.pgc.book.dto.RentalDTO;
import com.pgc.book.mapper.RentalMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RentalServiceImpl implements RentalService{

    private final RentalMapper rentalMapper;

    @Transactional
    @Override
    public RentalDTO registerRental(RentalDTO rental) {
        rentalMapper.insertRental(rental);
        return rental;
    }

    @Override
    public List<RentalDTO> getAllRentals() {
        return rentalMapper.findAllRentals();
    }

    @Override
    public RentalDTO getRentalById(int rentalId) {
        return rentalMapper.findRentalById(rentalId);
    }



    @Override
    public List<BookDTO> getUnreturnedBooks(){
        return rentalMapper.findUnreturnedBooks();
    }
}
