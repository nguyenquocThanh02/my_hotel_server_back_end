package com.myhotel.hotel.response;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.Room;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class BookedResponse {
    private Long id;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private String userName;
    private String userEmail;
    private int userAmount;
    private String bookingConfirmCode;
    private boolean isBilled;
    private RoomResponse room;

    public BookedResponse(Long id, LocalDate checkIn, LocalDate checkOut, String userName,
                          String userEmail, int userAmount, String bookingConfirmCode, boolean isBilled) {
        this.id = id;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.userName = userName;
        this.userEmail = userEmail;
        this.userAmount = userAmount;
        this.bookingConfirmCode = bookingConfirmCode;
        this.isBilled = isBilled;
    }


//    public void setRoom(RoomResponse room) {
//
//    }
}
