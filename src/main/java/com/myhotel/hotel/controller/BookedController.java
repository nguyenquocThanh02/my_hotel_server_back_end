package com.myhotel.hotel.controller;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.service.IBookedService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        if(userEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email empty!");
        }
        return bookedService.createBooking(roomId, userEmail, BookingRequest);
    }

    @GetMapping("/get/booked/of/{userEmail}")
    public ResponseEntity<?> getBookedsByUserEmail(@PathVariable String userEmail){
        return bookedService.getBookedsByUserEmail(userEmail);
    }


    @GetMapping("/get/all/booked")
    public ResponseEntity<?> getAllBookeds(){
        return bookedService.getAllBookeds();
    }

    @DeleteMapping("/delete/booked/{bookedId}")
    public ResponseEntity<?> deleteBookedsById(@PathVariable Long bookedId){
        return bookedService.deleteBookedsById(bookedId);
    }
}

