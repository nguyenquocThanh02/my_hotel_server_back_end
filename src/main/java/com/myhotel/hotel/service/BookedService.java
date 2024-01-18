package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.repository.BookedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookedService implements IBookedService{
    private final BookedRepository bookedRepository;
    @Override
    public List<Booked> getBookedByRoomPrice() {
        return bookedRepository.findAll();
    }
}
