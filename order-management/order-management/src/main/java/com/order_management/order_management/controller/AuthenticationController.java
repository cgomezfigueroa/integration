package com.order_management.order_management.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

  private final String SECRET_KEY = "ajsicuabijas812j1o4u18770aasnc98";

  @PostMapping("/auth/login")
  public String login() {
    Key secureKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    return Jwts.builder()
      .setSubject("test-user")
      .signWith(secureKey, SignatureAlgorithm.HS256)
      .compact();
  }
}
