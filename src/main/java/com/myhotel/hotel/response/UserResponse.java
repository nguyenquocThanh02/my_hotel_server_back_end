package com.myhotel.hotel.response;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
    private Long id;
    private String userName;
    private String userEmail;

    public UserResponse(Long id, String userName, String userEmail){
        this.id = id;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
