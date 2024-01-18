package com.myhotel.hotel.service;

import com.myhotel.hotel.model.User;
import com.myhotel.hotel.repository.UserRepository;
import com.myhotel.hotel.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{


    private final UserRepository userRepository;
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
        return ResponseEntity.ok(theUser);
    }
}
