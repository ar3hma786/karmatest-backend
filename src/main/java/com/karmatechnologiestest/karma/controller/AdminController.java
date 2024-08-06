package com.karmatechnologiestest.karma.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.karmatechnologiestest.karma.entities.Admin;
import com.karmatechnologiestest.karma.exception.AdminException;
import com.karmatechnologiestest.karma.service.AdminService;

@RestController
@RequestMapping("/api/users")
public class AdminController {
    
    @Autowired
    private AdminService userService;
    
    @GetMapping("/{userId}")
    public ResponseEntity<Admin> findUserById(@RequestHeader("Authorization") String jwt, @PathVariable Long userId) throws AdminException {
        Admin user = userService.findUserById(userId);
        if (user == null) {
            throw new AdminException("User not found with id: " + userId);
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/profile")
    public ResponseEntity<Admin> findUserProfileByJwt(@RequestHeader("Authorization") String jwt) throws AdminException {
        Admin user = userService.findUserProfileByJwt(jwt);
        if (user == null) {
            throw new AdminException("User profile not found for the provided JWT");
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping("/email/{email}")
    public ResponseEntity<Admin> findUserByEmail(@PathVariable String username) throws AdminException {
        Admin user = userService.findUserByUsername(username);
        if (user == null) {
            throw new AdminException("User not found with email: " + username);
        }
        return ResponseEntity.ok(user);
    }
    
    @GetMapping
    public ResponseEntity<List<Admin>> findAllUsers() {
        List<Admin> users = userService.findAllUsers();
        return ResponseEntity.ok(users);      
    }
    
}