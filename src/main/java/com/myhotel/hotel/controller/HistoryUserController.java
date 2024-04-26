package com.myhotel.hotel.controller;

import com.myhotel.hotel.service.IHistoryUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryUserController {

    final private IHistoryUserService historyUserService;

    @GetMapping("/of/{userEmail}")
    public ResponseEntity<?> getHistorysByUserEmail(@PathVariable String userEmail){
        return historyUserService.getHistorysByUserEmail(userEmail);
    }
}
