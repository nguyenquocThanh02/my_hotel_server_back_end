package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Booked;
import com.myhotel.hotel.model.History_user;
import com.myhotel.hotel.model.User;
import com.myhotel.hotel.repository.HistoryUserRepository;
import com.myhotel.hotel.repository.UserRepository;
import com.myhotel.hotel.response.BookedResponse;
import com.myhotel.hotel.response.HistoryUserResponse;
import com.myhotel.hotel.response.RoomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HistoryUserService implements IHistoryUserService{

    final private HistoryUserRepository historyUserRepository;
    final private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getHistorysByUserEmail(String userEmail) {
        User theUser = userRepository.findByUserEmail(userEmail);

        List<History_user> historyUser = historyUserRepository.findAllByUserId(theUser.getId());

        List<HistoryUserResponse> responses = new ArrayList<>();

        for (History_user hUser : historyUser) {
            HistoryUserResponse historyUserResponse = new HistoryUserResponse(
                    hUser.getId(), hUser.getAction(), hUser.getTime()
            );
            responses.add(historyUserResponse);
        }

        return ResponseEntity.ok(responses);
    }
}
