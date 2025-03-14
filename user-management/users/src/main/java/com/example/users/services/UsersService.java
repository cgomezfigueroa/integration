package com.example.users.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.users.entities.User;
import com.example.users.repositories.UsersRepository;
import jakarta.validation.Valid;

@Service
public class UsersService {
    
    @Autowired
    private UsersRepository usersRepository;

    public ResponseEntity<User> userDetails(Integer userId){
        Optional<User> optionalUser = usersRepository.findById(userId);
        if(optionalUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.status(HttpStatus.OK).body(optionalUser.get());
    }

    public User registerUser(@Valid @RequestBody User user){
        return usersRepository.save(user);
    }

    public ResponseEntity<User> updateUser(@Valid @RequestBody User user){
        Optional<User> oldUser = usersRepository.findById(user.getId());
        if(oldUser.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        usersRepository.delete(oldUser.get());
        usersRepository.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(oldUser.get());
    }

}
