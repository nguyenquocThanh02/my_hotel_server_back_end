package com.myhotel.hotel.controller;

import com.myhotel.hotel.service.IAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final IAdminService adminService;
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestParam("adminName") String adminName,
                                      @RequestParam("adminEmail") String adminEmail,
                                      @RequestParam("adminRole") String adminRole,
                                      @RequestParam("adminPassword") String adminPassword,
                                      @RequestParam("adminConfirmPassword") String adminConfirmPassword){
        return adminService.register(adminName, adminEmail, adminRole, adminPassword, adminConfirmPassword);
    }

    @PostMapping("/login")
    public ResponseEntity<?> Login(@RequestParam("adminEmail") String adminEmail,
                                       @RequestParam("adminPassword") String adminPassword){
        return adminService.Login(adminEmail, adminPassword);
    }
}
