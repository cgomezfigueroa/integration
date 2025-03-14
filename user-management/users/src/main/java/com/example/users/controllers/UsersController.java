package com.example.users.controllers;

import java.security.Key;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.users.entities.User;
import com.example.users.services.UsersService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;



@RestController
@RequestMapping("/api/users")
public class UsersController {
    
    @Autowired
    UsersService usersService;
    
    @PostMapping("/register")
    public User postUser(@RequestBody User user) {
        //TODO: process POST request
        return usersService.registerUser(user);
    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {
        //TODO: process POST request
        Key secureKey = Keys.hmacShaKeyFor("BcfWPFzRvL04zNqZdEfj4CP01/WfEecQj7N03s/byqs=".getBytes());
        return Jwts.builder().setSubject(user.getId().toString()).signWith(secureKey,SignatureAlgorithm.HS256).compact();
    }
    
    @GetMapping("/{userId}")
    public ResponseEntity<?> getUser(@PathVariable Integer userId) {
        return usersService.userDetails(userId);
    }
    
    @PutMapping("/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Integer userId, @RequestBody User user) {
        user.setId(userId);
        return usersService.updateUser(user);
    }
    
}
