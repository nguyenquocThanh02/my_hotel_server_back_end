package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Bill;
import org.springframework.http.ResponseEntity;

import java.time.YearMonth;

public interface IBillService {
    ResponseEntity<?> createBill(Long bookedId, String adminEmail, Bill bill);

    ResponseEntity<?> getAllBills(boolean param);

    ResponseEntity<?> completeBill(Long billId);

    ResponseEntity<?> unCompleteBill(Long billId);

    ResponseEntity<?> getReport(YearMonth selectedMonth);
}
