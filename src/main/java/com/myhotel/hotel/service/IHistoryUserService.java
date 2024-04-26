package com.myhotel.hotel.service;

import org.springframework.http.ResponseEntity;

public interface IHistoryUserService {
    ResponseEntity<?> getHistorysByUserEmail(String userEmail);
}
