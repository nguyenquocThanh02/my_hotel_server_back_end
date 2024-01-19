package com.myhotel.hotel.controller;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.service.IBookedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookedController {

    private final IBookedService bookedService;

    @PostMapping("/{roomId}/by/{userEmail}")
    public ResponseEntity<?> createBooking(@PathVariable Long roomId, @PathVariable String userEmail,
                                           @RequestBody Booked BookingRequest){
        return bookedService.createBooking(roomId, userEmail, BookingRequest);
    }
}
