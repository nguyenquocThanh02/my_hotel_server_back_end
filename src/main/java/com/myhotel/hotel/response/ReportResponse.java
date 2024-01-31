package com.myhotel.hotel.response;

import lombok.*;

import java.math.BigDecimal;

@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private BigDecimal revenue;
    private int countBill;
    
}
