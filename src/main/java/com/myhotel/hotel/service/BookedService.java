package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.Room;
import com.myhotel.hotel.model.User;
import com.myhotel.hotel.repository.BookedRepository;
import com.myhotel.hotel.repository.RoomRepository;
import com.myhotel.hotel.repository.UserRepository;
import com.myhotel.hotel.response.BookedResponse;
import com.myhotel.hotel.response.ErrorResponse;
import com.myhotel.hotel.response.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookedService implements IBookedService{

    private final BookedRepository bookedRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;

    @Override
    public ResponseEntity<?> createBooking(Long roomId, String userEmail, Booked bookingRequest) {
        Optional<Room> theRoom = roomRepository.findById(roomId);

        if(userEmail.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User email can not empty");
        }
        User theUser = userRepository.findByUserEmail(userEmail);

        if(theUser == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Can not find the user!");
        }

        if(!theRoom.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Room does not exist!!"));
        }



        if(bookingRequest.getCheckOut().isBefore(bookingRequest.getCheckIn()) ||
            bookingRequest.getCheckOut().isEqual(bookingRequest.getCheckIn())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Check out day must after check in day!!"));
        }

        List<Booked> existBookings = bookedRepository.findAllByRoom_Id(roomId);

        boolean Available = checkRoomIsAvailable(bookingRequest, existBookings);
        if(Available){
            bookingRequest.setRoom(theRoom.get());
            bookingRequest.setUser(theUser);
            bookingRequest.setBookingConfirmCode(RandomStringUtils.randomNumeric(5));
            bookedRepository.save(bookingRequest);

//            confirm by send mail
            emailService.sendMailBookingConfirmation(bookingRequest.getUserEmail(), bookingRequest.getBookingConfirmCode(), bookingRequest.getUserName());

            return ResponseEntity.ok().body("Congratulation successfully to booking: "+ bookingRequest.getBookingConfirmCode());
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Room is not available for day!"));
        }
    }

    private boolean checkRoomIsAvailable(Booked bookingRequest, List<Booked> existBookings) {
        return existBookings.stream()
                .noneMatch(existBooking ->
                        bookingRequest.getCheckIn().equals(existBooking.getCheckIn())
                                || bookingRequest.getCheckOut().equals(existBooking.getCheckOut())

                                || (bookingRequest.getCheckIn().isAfter(existBooking.getCheckIn())
                                && bookingRequest.getCheckIn().isBefore(existBooking.getCheckOut()))

                                || (bookingRequest.getCheckIn().isBefore(existBooking.getCheckIn())
                                && bookingRequest.getCheckOut().isAfter(existBooking.getCheckIn()))

                                || (bookingRequest.getCheckOut().isAfter(existBooking.getCheckOut())
                                && bookingRequest.getCheckIn().isBefore(existBooking.getCheckOut()))
                );
    }

    @Override
    public List<Booked> getBookedByRoomPrice() {
        return bookedRepository.findAll();
    }

    @Override
    public ResponseEntity<?> getBookedsByUserEmail(String userEmail) {
        User theUser = userRepository.findByUserEmail(userEmail);

        List<Booked> bookeds = bookedRepository.findAllExceptPrintedBillByUser_Id(theUser.getId());

        if(bookeds.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List booked is empty!!");
        }
        List<BookedResponse> bookedResponses = new ArrayList<>();

        for (Booked booked : bookeds) {
            BookedResponse bookedResponse = new BookedResponse();
            bookedResponse.setId(booked.getId());
            bookedResponse.setUserName(booked.getUserName());
            bookedResponse.setUserEmail(booked.getUserEmail());
            bookedResponse.setUserAmount(booked.getUserAmount());
            bookedResponse.setBookingConfirmCode(booked.getBookingConfirmCode());
            bookedResponse.setCheckIn(booked.getCheckIn());
            bookedResponse.setCheckOut(booked.getCheckOut());

            RoomResponse theRoomResponse = getRoomResponse(booked);
            bookedResponse.setRoom(theRoomResponse);

            bookedResponses.add(bookedResponse);
        }

        return ResponseEntity.ok(bookedResponses);
    }



    @Override
    public ResponseEntity<?> getAllBookeds() {
        List<Booked> bookeds = bookedRepository.findAllExceptPrintedBill();

        if(bookeds.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("List booked is empty!!");
        }
        List<BookedResponse> bookedResponses = new ArrayList<>();

        for (Booked booked : bookeds) {
            BookedResponse bookedResponse = new BookedResponse();
            bookedResponse.setId(booked.getId());
            bookedResponse.setUserName(booked.getUserName());
            bookedResponse.setUserEmail(booked.getUserEmail());
            bookedResponse.setUserAmount(booked.getUserAmount());
            bookedResponse.setBookingConfirmCode(booked.getBookingConfirmCode());
            bookedResponse.setCheckIn(booked.getCheckIn());
            bookedResponse.setCheckOut(booked.getCheckOut());

            RoomResponse theRoomResponse = getRoomResponse(booked);
            bookedResponse.setRoom(theRoomResponse);

            bookedResponses.add(bookedResponse);
        }

        return ResponseEntity.ok(bookedResponses);
    }

    private RoomResponse getRoomResponse(Booked booked){
//        Optional<Room> theRoom = roomRepository.findById(booked.getRoom().getId());

//        RoomResponse roomResponse = new RoomResponse(theRoom.get().getId(), theRoom.get().getRoomType(),
//                theRoom.get().getRoomPrice(), theRoom.get().getRoomDetails());
        RoomResponse roomResponse = new RoomResponse(booked.getRoom().getId(), booked.getRoom().getRoomType(),
                booked.getRoom().getRoomPrice(), booked.getRoom().getRoomDetails());

        return roomResponse;
    }

    @Override
    public ResponseEntity<?> deleteBookedsById(Long bookedId) {
        Optional<Booked> booked = bookedRepository.findById(bookedId);
        if(!booked.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("The booked does not exist!");
        }
        bookedRepository.deleteById(bookedId);
        return new ResponseEntity<>("Cancel booked successfully", HttpStatus.OK);
    }

    @Override
    public List<Booked> getAllBookingById(Long id) {
        return bookedRepository.findAllByRoom_Id(id);
    }


}

