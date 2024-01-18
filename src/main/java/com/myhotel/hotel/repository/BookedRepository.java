package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.Booked;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;

public interface BookedRepository extends JpaRepository<Booked, Long> {
//    @Query("SELECT b FROM Booked b WHERE b.room.roomPrice = :roomPrice")
}
