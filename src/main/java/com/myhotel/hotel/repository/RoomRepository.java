package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoomRepository extends JpaRepository<Room, Long> {
    @Query("select distinct roomType from Room")
    List<String> findDistinctRoomType();
}
