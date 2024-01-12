package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface IRoomService {
    Room addNewRoom(MultipartFile roomImage, String roomType, BigDecimal roomPrice, String roomDetails) throws IOException, SQLException;

    List<String> getAllRoomType();

    List<Room> getAllRoom();

    byte[] getImageRoomById(Long id) throws SQLException;

    void deleteRoom(Long roomId);

    Optional<Room> getRoomById(Long roomId);

    void updateRoom(Long roomId, MultipartFile roomImage, String roomType, BigDecimal roomPrice, String roomDetails) throws IOException, SQLException;
}
