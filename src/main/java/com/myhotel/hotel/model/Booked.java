package com.myhotel.hotel.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Booked {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String userName;
    private String userEmail;
    private int userAmount;
    private String bookingConfirmCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    public void setBookingConfirmCode(String bookingConfirmCode){
        this.bookingConfirmCode = bookingConfirmCode;
    }
}
