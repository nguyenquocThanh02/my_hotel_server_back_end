package com.myhotel.hotel.response;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.Room;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomDetails;
    private String roomImage;
    private boolean isBooked;
    private List<BookedResponse> bookeds;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, String roomDetails, List<BookedResponse> bookeds){
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDetails = roomDetails;
        this.bookeds = bookeds;
    }

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, String roomDetails){
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDetails = roomDetails;
    }

}
