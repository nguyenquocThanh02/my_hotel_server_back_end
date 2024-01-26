package com.myhotel.hotel.service;

import com.myhotel.hotel.model.Admin;
import com.myhotel.hotel.repository.AdminRepository;
import com.myhotel.hotel.response.AdminResponse;
import com.myhotel.hotel.response.ErrorResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService implements IAdminService{
    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<?> register(String adminName, String adminEmail, String adminRole, String adminPassword, String adminConfirmPassword) {
        if(adminName.isEmpty() || adminEmail.isEmpty() || adminPassword.isEmpty() || adminRole.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Required all parameter!!!"));
        }

        if(adminRepository.existsByAdminEmail(adminEmail)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email existed!!"));
        }

        if(adminPassword.length()<6){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Password is not strong!!"));
        }

        if(!adminPassword.equals(adminConfirmPassword)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Confirm password incorrect!!"));
        }

        Admin newAdmin = new Admin();

        newAdmin.setAdminEmail(adminEmail);
        newAdmin.setAdminName(adminName);
        newAdmin.setAdminRole(adminRole);
        newAdmin.setAdminPassword(passwordEncoder.encode(adminPassword));
        adminRepository.save(newAdmin);
        return ResponseEntity.ok(newAdmin);
    }

    @Override
    public ResponseEntity<?> Login(String adminEmail, String adminPassword) {
        if(!adminRepository.existsByAdminEmail(adminEmail)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Email do not exist!!"));
        }
        Admin theAdmin = adminRepository.findByAdminEmail(adminEmail);
        if(!passwordEncoder.matches(adminPassword, theAdmin.getAdminPassword())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("Password incorrect!!"));
        }

        AdminResponse adminResponse = new AdminResponse(
            theAdmin.getId(), theAdmin.getAdminName(),
                theAdmin.getAdminEmail(), theAdmin.getAdminRole()
        );
        return ResponseEntity.ok(adminResponse);
    }
}
