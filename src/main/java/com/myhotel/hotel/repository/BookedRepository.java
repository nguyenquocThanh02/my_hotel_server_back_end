package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.Booked;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookedRepository extends JpaRepository<Booked, Long> {
}
