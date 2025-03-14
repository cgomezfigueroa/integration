package com.example.users.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.users.entities.User;

public interface UsersRepository extends JpaRepository<User, Integer>{
    
}
