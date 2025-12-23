package com.kaikdev.Tera.service.Admin;

import com.kaikdev.Tera.exception.AdminException;
import com.kaikdev.Tera.model.Dto.AdminRequest;
import com.kaikdev.Tera.model.Entity.User;
import com.kaikdev.Tera.model.Enum.Role;
import com.kaikdev.Tera.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public User newAdmin(AdminRequest adminRequest) {

        if (adminRequest.getUsername() == null) {
            throw new AdminException(HttpStatus.BAD_REQUEST,"USername nao pode ser null");
        }

        if (userRepository.existsByEmail(adminRequest.getEmail())){
            throw new RuntimeException("Email ja cadastrado");
        }

       User user = User.builder()
                .email(adminRequest.getEmail())
                .username(adminRequest.getUsername())
                .password(passwordEncoder.encode(adminRequest.getPassword()))
               .role(Role.ROLE_ADMIN)
               .build();
       return userRepository.save(user);

    }
}
