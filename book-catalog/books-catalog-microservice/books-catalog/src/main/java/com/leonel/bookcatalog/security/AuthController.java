package com.leonel.bookcatalog.security;

import java.security.Key;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@RestController
@RequestMapping("/api")
public class AuthController {

	@Value("${jwt_secret}")
	private String SECRET;
	@Value("${jwt_user}")
	private String USER;
	
    @PostMapping("/auth/login")
    public String login() {
        Key secureKey = Keys.hmacShaKeyFor(SECRET.getBytes());

        return Jwts.builder().setSubject(USER).signWith(secureKey, SignatureAlgorithm.HS256).compact();
    }
}