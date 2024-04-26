package com.myhotel.hotel.service;

import com.myhotel.hotel.model.History_user;
import com.myhotel.hotel.model.User;
import com.myhotel.hotel.repository.HistoryUserRepository;
import com.myhotel.hotel.repository.UserRepository;
import com.myhotel.hotel.response.ErrorResponse;
import com.myhotel.hotel.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{


    private final UserRepository userRepository;
    private final HistoryUserRepository historyUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> register(String userName, String userEmail, String userPassword,String userConfirmPassword) {

        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Required all parameter!!!"));
        }

        if(userRepository.existsByUserEmail(userEmail)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email existed!!"));
        }

        if(userPassword.length()<6){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Password is not strong!!"));
        }

        if(!userConfirmPassword.equals(userPassword)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Confirm password incorrect!!"));
        }

        User newUser = new User();

        newUser.setUserEmail(userEmail);
        newUser.setUserName(userName);
        newUser.setUserPassword(passwordEncoder.encode(userPassword));
        userRepository.save(newUser);

        //save history register
        History_user historyUser = new History_user();
        historyUser.setAction("Register");
        historyUser.setTime(LocalDateTime.now());
        historyUser.setUser(newUser);

        historyUserRepository.save(historyUser);
        return ResponseEntity.ok(newUser);
//        return ResponseEntity.ok(userRepository.save(newUser));
    }

    @Override
    public ResponseEntity<?> LoginUser(String userEmail, String userPassword) {
        if(!userRepository.existsByUserEmail(userEmail)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email do not exist!!"));
        }
        User theUser = userRepository.findByUserEmail(userEmail);
        if(!passwordEncoder.matches(userPassword, theUser.getUserPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Password incorrect!!"));
        }
        UserResponse theUserResponse = new UserResponse(theUser.getId(),
                theUser.getUserName(), theUser.getUserEmail());

//    Save history login
        History_user historyUser = new History_user();
        historyUser.setAction("Login");
        historyUser.setTime(LocalDateTime.now());
        historyUser.setUser(theUser);

        historyUserRepository.save(historyUser);

        return ResponseEntity.ok(theUserResponse);
    }
}
