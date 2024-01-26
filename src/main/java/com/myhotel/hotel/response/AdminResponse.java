package com.myhotel.hotel.response;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdminResponse {
    private Long id;
    private String adminName;
    private String adminEmail;
    private String adminRole;
}
