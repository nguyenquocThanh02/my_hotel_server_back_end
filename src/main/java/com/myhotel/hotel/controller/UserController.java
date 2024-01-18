package com.myhotel.hotel.controller;

import com.myhotel.hotel.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final IUserService userService;
    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestParam("userName") String userName,
                                         @RequestParam("userEmail") String userEmail,
                                         @RequestParam("userPassword") String userPassword,
                                      @RequestParam("userConfirmPassword") String userConfirmPassword){
        return userService.register(userName, userEmail, userPassword, userConfirmPassword);
    }

    @PostMapping("/login")
    public ResponseEntity<?> LoginUser(@RequestParam("userEmail") String userEmail,
                                          @RequestParam("userPassword") String userPassword){
        return userService.LoginUser(userEmail, userPassword);
    }
}
