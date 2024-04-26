package com.myhotel.hotel.response;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class HistoryUserResponse {
    private Long id;
    private String action;
    private LocalDateTime time;
}
