package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserEmail(String userEmail);

    User findByUserEmail(String userEmail);
}
