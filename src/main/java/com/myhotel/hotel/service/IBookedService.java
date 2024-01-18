package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Booked;

import java.math.BigDecimal;
import java.util.List;

public interface IBookedService {
    List<Booked> getBookedByRoomPrice();
}
