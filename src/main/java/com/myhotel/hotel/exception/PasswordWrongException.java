package com.myhotel.hotel.exception;

public class PasswordWrongException extends RuntimeException {
    public PasswordWrongException(String s) {
        super(s);
    }
}
