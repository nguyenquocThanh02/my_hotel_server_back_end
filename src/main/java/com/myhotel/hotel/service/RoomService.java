package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Room;
import com.myhotel.hotel.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoomService implements IRoomService {
    private final RoomRepository roomRepository;

    @Override
    public List<Room> getAllRoom() {
        return roomRepository.findAll();
    }

    @Override
    public byte[] getImageRoomById(Long id) throws SQLException {
        Optional<Room> theRoom = roomRepository.findById(id);
        if(!theRoom.isEmpty()){
            Blob imageBlob = theRoom.get().getRoomImage();
            if(imageBlob != null)
                return imageBlob.getBytes(1, (int) imageBlob.length());
        }
        return null;
    }

    @Override
    public Room addNewRoom(MultipartFile roomImage, String roomType, BigDecimal roomPrice, String roomDetails) throws IOException, SQLException {
        Room room= new Room();
        room.setRoomDetails(roomDetails);
        room.setRoomPrice(roomPrice);
        room.setRoomType(roomType);
        if(!roomImage.isEmpty()){
            byte[] imageBytes = roomImage.getBytes();
            Blob imageBlob = new SerialBlob(imageBytes);
            room.setRoomImage(imageBlob);
        }
        return roomRepository.save(room);
    }

    @Override
    public List<String> getAllRoomType() {
        return roomRepository.findDistinctRoomType();
    }

}
