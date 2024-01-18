package com.myhotel.hotel.controller;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.service.IBookedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class BookedController {

    private final IBookedService bookedService;
    @GetMapping("/get/book")
    public ResponseEntity<String> getBookedByRoomPrice() {
        List<Booked> book = bookedService.getBookedByRoomPrice();
//        return ResponseEntity.ok(book);
        String trave = null;
        for (Booked b : book) {
            trave = b.getUserEmail();
        }
        return ResponseEntity.ok(trave);
    }
}
