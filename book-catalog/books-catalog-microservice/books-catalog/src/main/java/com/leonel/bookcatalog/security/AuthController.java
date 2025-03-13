package com.leonel.bookcatalog.security;

import java.security.Key;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
public class AuthController {

    @PostMapping("/auth/login")
    public String login() {
        Key secureKey = Keys.hmacShaKeyFor("BcfWPFzRvL04zNqZdEfj4CP01/WfEecQj7N03s/byqs=".getBytes());

        return Jwts.builder().setSubject("test-user").signWith(secureKey, SignatureAlgorithm.HS256).compact();
    }
}