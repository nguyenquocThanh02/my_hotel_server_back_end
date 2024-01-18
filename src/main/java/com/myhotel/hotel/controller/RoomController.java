package com.myhotel.hotel.controller;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.Room;
import com.myhotel.hotel.response.RoomResponse;
import com.myhotel.hotel.service.IRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import org.apache.tomcat.util.codec.binary.Base64;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/rooms")
public class RoomController {

    private final IRoomService roomService;

    @PostMapping("/add/room")
    public ResponseEntity<Room> addNewRoom(
            @RequestParam("roomImage") MultipartFile roomImage,
            @RequestParam("roomType") String roomType,
            @RequestParam("roomPrice") BigDecimal roomPrice,
            @RequestParam("roomDetails") String roomDetails) throws IOException, SQLException {
        Room newRoom = roomService.addNewRoom(roomImage, roomType, roomPrice, roomDetails);
        return ResponseEntity.ok(newRoom);
    }

    @GetMapping("get/type")
    public List<String> getAllRoomType(){
        return roomService.getAllRoomType();
    }

    @GetMapping("get/{roomId}")
    public ResponseEntity<RoomResponse> getRoomById(@PathVariable Long roomId) throws SQLException {
        Optional<Room> room = roomService.getRoomById(roomId);
        byte[] imageByte = roomService.getImageRoomById(room.get().getId());
        String base64Photo = "";
        if(imageByte != null && imageByte.length > 0){
            base64Photo = Base64.encodeBase64String(imageByte);
        }
        RoomResponse response = getRoomResponse(room.get());
        response.setRoomImage(base64Photo);
        return ResponseEntity.ok(response);
    }
    @GetMapping("get-all/room")
    public ResponseEntity<List<RoomResponse>> getAllRoom() throws SQLException {
        List<Room> rooms = roomService.getAllRoom();
        List<RoomResponse> responses = new ArrayList<>();
        for(Room room : rooms){
            byte[] imageBytes = roomService.getImageRoomById(room.getId());
            if(imageBytes != null && imageBytes.length>0){
                String base64Photo = Base64.encodeBase64String(imageBytes);
                RoomResponse response = getRoomResponse(room);
                response.setRoomImage(base64Photo);
                responses.add(response);
            }
        }
        return ResponseEntity.ok(responses);
    }

    private RoomResponse getRoomResponse(Room room) {
//        List<Booked> bookings = getAllBookingById(room.getId());
        return new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(), room.getRoomDetails());
    }

    @DeleteMapping("/delete/{roomId}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId){
        roomService.deleteRoom(roomId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{roomId}")
    public ResponseEntity<Void> updateRoom(@PathVariable Long roomId,
                                           @RequestParam(required = false) MultipartFile roomImage,
                                           @RequestParam(required = false) String roomType,
                                           @RequestParam(required = false) BigDecimal roomPrice,
                                           @RequestParam(required = false) String roomDetails) throws SQLException, IOException {
        byte[] photoBytes = roomImage != null && !roomImage.isEmpty() ?
                roomImage.getBytes() : roomService.getImageRoomById(roomId);
        roomService.updateRoom(roomId, photoBytes, roomType, roomPrice, roomDetails);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
