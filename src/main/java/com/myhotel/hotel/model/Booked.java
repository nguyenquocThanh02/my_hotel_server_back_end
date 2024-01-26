package com.myhotel.hotel.model;

import jakarta.persistence.*;
import lombok.*;

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
    private boolean isBilled = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(mappedBy = "booked", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Bill bill;

    public void setIsBilled(boolean isBilled){
        this.isBilled = isBilled;
    }

    public boolean getIsBilled(){
        return this.isBilled;
    }
    public void setBookingConfirmCode(String bookingConfirmCode){
        this.bookingConfirmCode = bookingConfirmCode;
    }

}
