package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.Booked;
import jakarta.websocket.server.PathParam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface BookedRepository extends JpaRepository<Booked, Long> {

    List<Booked> findAllByRoom_Id(Long roomId);

//    List<Booked> findAllByUser_Id(Long id);

    @Query("SELECT b.room.id FROM Booked b WHERE b.id = :id")
    Long getRoom_id_booked(Long id);

    @Query("SELECT b FROM Booked b WHERE b.isBilled = false")
    List<Booked> findAllExceptPrintedBill();

    @Query("SELECT b FROM Booked b WHERE b.isBilled = false and b.user.id = :id")
    List<Booked> findAllExceptPrintedBillByUser_Id(Long id);


//    @Query("SELECT b FROM Booked b WHERE b.user.userEmail = :userEmail")
//    List<Booked> findBookedByMainUserEmail(String userEmail);

//    @Query("SELECT b FROM Booked b WHERE b.room.roomPrice = :roomPrice")
}
