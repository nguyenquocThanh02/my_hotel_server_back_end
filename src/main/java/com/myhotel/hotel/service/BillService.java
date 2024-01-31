package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Admin;
import com.myhotel.hotel.model.Bill;
import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.repository.AdminRepository;
import com.myhotel.hotel.repository.BillRepository;
import com.myhotel.hotel.repository.BookedRepository;
import com.myhotel.hotel.response.AdminResponse;
import com.myhotel.hotel.response.BillResponse;
import com.myhotel.hotel.response.BookedResponse;
import com.myhotel.hotel.response.ReportResponse;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BillService implements IBillService{


    private final BillRepository billRepository;
    private final BookedRepository bookedRepository;
    private final AdminRepository adminRepository;

    @Override
    public ResponseEntity<?> createBill(Long bookedId, String adminEmail, Bill bill) {
        Optional<Booked> theBooked = bookedRepository.findById(bookedId);
        Admin theAdmin = adminRepository.findByAdminEmail(adminEmail);

        if(!theBooked.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not access the booked!");
        }

        if(theBooked.get().getIsBilled()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You can create again! Booked has created!");
        }

        if(theAdmin == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not find the admin!");
        }

        bill.setAdmin(theAdmin);
        bill.setBooked(theBooked.get());
        billRepository.save(bill);

        theBooked.get().setIsBilled(true);
        bookedRepository.save(theBooked.get());

        return ResponseEntity.ok().body("Create bill success!");
    }

    @Override
    public ResponseEntity<?> completeBill(Long billId) {
        Optional<Bill> theBill = billRepository.findById(billId);

        if(!theBill.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill id does not exist!");
        }

        theBill.get().setPaid(true);
        theBill.get().setTimePrintBill(LocalDateTime.now());

        billRepository.save(theBill.get());

        return ResponseEntity.ok("Complete the bill");
    }

    @Override
    public ResponseEntity<?> unCompleteBill(Long billId) {
        Optional<Bill> theBill = billRepository.findById(billId);

        if(!theBill.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill id does not exist!");
        }

//        if ( theBill.get().getTimePrintBill().isBefore(LocalDateTime.now()) ) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("You can't change bill in past!");
//        }

        theBill.get().setPaid(false);
        theBill.get().setTimePrintBill(null);

        billRepository.save(theBill.get());

        return ResponseEntity.ok("Un complete the bill");
    }

    @Override
    public ResponseEntity<?> getAllBills(boolean param) {
        List<Bill> theBills;

        if(!param){
            theBills = billRepository.findAllExceptPaid();
        }else {
            theBills = billRepository.findAllPaid();
        }

        if(theBills.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bill is empty!");
        }

        List<BillResponse> billResponses = new ArrayList<>();

        for(Bill bill : theBills){
            BillResponse billResponse = new BillResponse();

            billResponse.setId(bill.getId());
            billResponse.setTimePrintBill(bill.getTimePrintBill());
            billResponse.setPaid(bill.getPaid());
            billResponse.setTotalPrice(bill.getTotalPrice());

            BookedResponse bookedResponse = getBookedResponse(bill);
            billResponse.setBooked(bookedResponse);

            AdminResponse adminResponse = getAdminResponse(bill);
            billResponse.setAdmin(adminResponse);

            billResponses.add(billResponse);
        }

        return ResponseEntity.ok(billResponses);
    }

    private BookedResponse getBookedResponse(Bill bill){
        return new BookedResponse(
                bill.getBooked().getId(),
                bill.getBooked().getCheckIn(),
                bill.getBooked().getCheckOut(),
                bill.getBooked().getUserName(),
                bill.getBooked().getUserEmail(),
                bill.getBooked().getUserAmount(),
                bill.getBooked().getBookingConfirmCode(),
                bill.getBooked().getIsBilled()
        );
    }

    private AdminResponse getAdminResponse(Bill bill){
        return new AdminResponse(
                bill.getAdmin().getId(),
                bill.getAdmin().getAdminName(),
                bill.getAdmin().getAdminEmail(),
                bill.getAdmin().getAdminRole()
        );
    }

    @Override
    public ResponseEntity<?> getReport(YearMonth selectedMonth) {
        List<Bill> bills = billRepository.findAllPaid();

        BigDecimal sum = BigDecimal.ZERO;
        Integer count = 0;

        for(Bill bill : bills){
            YearMonth billYearMonth = YearMonth.from(bill.getTimePrintBill());
            if(billYearMonth.equals(selectedMonth)){
                sum = sum.add(bill.getTotalPrice());
                count = count + 1;
            }
        }

        ReportResponse reportResponse = new ReportResponse(sum, count);
        return ResponseEntity.ok(reportResponse);
    }
}
