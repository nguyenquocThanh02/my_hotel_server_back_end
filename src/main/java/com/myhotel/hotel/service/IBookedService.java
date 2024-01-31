package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Booked;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface IBookedService {
    List<Booked> getBookedByRoomPrice();

    ResponseEntity<?> createBooking(Long roomId, String userEmail, Booked bookingRequest);

    ResponseEntity<?> getBookedsByUserEmail(String userEmail);

    ResponseEntity<?> getAllBookeds();

    ResponseEntity<?> deleteBookedsById(Long bookedId);

    List<Booked> getAllBookingById(Long id);
}
