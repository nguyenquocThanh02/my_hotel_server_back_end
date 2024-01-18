package com.myhotel.hotel.exception;

public class UserEmailExistException extends RuntimeException {
    public UserEmailExistException(String ms) {
        super(ms);
    }
}
