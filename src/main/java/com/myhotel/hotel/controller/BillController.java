package com.myhotel.hotel.controller;

import com.myhotel.hotel.model.Bill;
import com.myhotel.hotel.service.IBillService;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bill")

public class BillController {

    private final IBillService billService;

    @PostMapping("/create/{bookedId}/by/{adminEmail}")
    public ResponseEntity<?> createBill(@PathVariable Long bookedId, @PathVariable String adminEmail,
                                        @RequestBody Bill bill){
        if(adminEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Admin email empty!");
        }
        return billService.createBill(bookedId, adminEmail, bill);
    }

    @PutMapping("/complete/{billId}")
    public ResponseEntity<?> completeBill(@PathVariable Long billId){
        return billService.completeBill(billId);
    }

    @PutMapping("/un/complete/{billId}")
    public ResponseEntity<?> unCompleteBill(@PathVariable Long billId){
        return billService.unCompleteBill(billId);
    }

    @GetMapping("/get/all")
    public ResponseEntity<?> getAllBills(@RequestParam(defaultValue = "false") boolean param){
        return billService.getAllBills(param);
    }

//    public BigDecimal getSaleFigure(@Parameter())
}
