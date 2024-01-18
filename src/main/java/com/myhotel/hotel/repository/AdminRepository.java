package com.myhotel.hotel.repository;

import com.myhotel.hotel.model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    boolean existsByAdminEmail(String adminEmail);

    Admin findByAdminEmail(String adminEmail);
}
