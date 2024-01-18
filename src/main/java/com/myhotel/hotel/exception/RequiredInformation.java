package com.myhotel.hotel.exception;

public class RequiredInformation extends RuntimeException {
    public RequiredInformation(String s) {
        super(s);
    }
}
