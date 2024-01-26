package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill, Long> {
    @Query("SELECT b FROM Bill b WHERE b.paid = false")
    List<Bill> findAllExceptPaid();
}
