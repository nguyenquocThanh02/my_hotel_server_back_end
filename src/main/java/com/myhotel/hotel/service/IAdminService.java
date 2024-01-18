package com.myhotel.hotel.service;

import org.springframework.http.ResponseEntity;

public interface IAdminService {
    ResponseEntity<?> register(String adminName, String adminEmail, String adminRole, String adminPassword, String adminConfirmPassword);

    ResponseEntity<?> Login(String adminEmail, String adminPassword);
}
