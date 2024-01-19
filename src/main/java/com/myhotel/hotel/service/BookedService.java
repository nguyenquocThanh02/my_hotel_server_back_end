package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.Room;
import com.myhotel.hotel.model.User;
import com.myhotel.hotel.repository.BookedRepository;
import com.myhotel.hotel.repository.RoomRepository;
import com.myhotel.hotel.repository.UserRepository;
import com.myhotel.hotel.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookedService implements IBookedService{


    private final BookedRepository bookedRepository;
    private final RoomRepository roomRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseEntity<?> createBooking(Long roomId, String userEmail, Booked bookingRequest) {
        Optional<Room> theRoom = roomRepository.findById(roomId);
        User theUser = userRepository.findByUserEmail(userEmail);

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
}
