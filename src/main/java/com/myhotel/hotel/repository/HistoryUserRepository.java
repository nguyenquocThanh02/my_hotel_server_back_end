package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.History_user;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryUserRepository extends JpaRepository<History_user, Long> {


    List<History_user> findAllByUserId(Long id);
}
