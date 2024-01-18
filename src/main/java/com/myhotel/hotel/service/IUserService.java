package com.myhotel.hotel.service;

import com.myhotel.hotel.model.User;
import org.springframework.http.ResponseEntity;

public interface IUserService {
    ResponseEntity<?> register(String userName, String userEmail, String userPassword, String userConfirmPassword);

    ResponseEntity<?> LoginUser(String userEmail, String userPassword);
}
