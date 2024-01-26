package com.myhotel.hotel.response;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.Room;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@Getter
@Setter
public class RoomResponse {
    private Long id;
    private String roomType;
    private BigDecimal roomPrice;
    private String roomDetails;
    private String roomImage;
    private boolean isBooked;
    private List<BookedResponse> bookeds;

    public RoomResponse(Long id, String roomType, BigDecimal roomPrice, String roomDetails){
        this.id = id;
        this.roomType = roomType;
        this.roomPrice = roomPrice;
        this.roomDetails = roomDetails;
    }

}
