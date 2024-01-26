package com.myhotel.hotel.response;

import com.myhotel.hotel.model.Admin;
import com.myhotel.hotel.model.Booked;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Getter
@Setter
@NoArgsConstructor
public class BillResponse {

    private Long id;
    private BigDecimal totalPrice;
    private LocalDateTime timePrintBill;
    private boolean paid = false;

    private BookedResponse booked;
    private AdminResponse admin;

}
