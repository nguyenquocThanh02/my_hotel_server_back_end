package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Room;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface IRoomService {
    Room addNewRoom(MultipartFile roomImage, String roomType, BigDecimal roomPrice, String roomDetails) throws IOException, SQLException;

    List<String> getAllRoomType();

    List<Room> getAllRoom();

    byte[] getImageRoomById(Long id) throws SQLException;
}
