package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Bill;
import org.springframework.http.ResponseEntity;

public interface IBillService {
    ResponseEntity<?> createBill(Long bookedId, String adminEmail, Bill bill);

    ResponseEntity<?> getAllBills();

    ResponseEntity<?> completeBill(Long billId);
}
