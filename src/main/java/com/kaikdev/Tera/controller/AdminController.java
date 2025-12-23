package com.kaikdev.Tera.controller;

import com.kaikdev.Tera.model.Dto.AdminRequest;
import com.kaikdev.Tera.model.Entity.User;
import com.kaikdev.Tera.service.Admin.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tera")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/new-admin")
    public ResponseEntity<User> newAdmin(@RequestBody AdminRequest adminRequest){
      User user =  adminService.newAdmin(adminRequest);
        return ResponseEntity.ok(user);
    }
}
