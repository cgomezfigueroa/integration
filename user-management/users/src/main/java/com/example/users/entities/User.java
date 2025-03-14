package com.example.users.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
@NoArgsConstructor
@AllArgsConstructor
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Size(min=2, message = "Name should have atleast 2 characters")
    private String name;

    @Size(min=2, message = "Name should have atleast 2 characters")
    private String lastnName;

    @Past(message = "Birth Date should be in the past")
    private LocalDate birthDate;

    @Size(min=5, message = "Address should have atleast 5 characters")
    private String address;
}
