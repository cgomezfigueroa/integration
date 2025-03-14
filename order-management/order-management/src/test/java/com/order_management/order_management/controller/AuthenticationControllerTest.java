package com.order_management.order_management.controller;

import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
public class AuthenticationControllerTest {

  @InjectMocks
  private AuthenticationController authenticationController;

  private MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
  }

  @Test
  void testLogin() throws Exception {
    mockMvc
      .perform(post("/auth/login").contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isOk())
      .andExpect(content().string(not(emptyOrNullString())));
  }

  @Test
  void testGeneratedToken() {
    String secretKey = "ajsicuabijas812j1o4u18770aasnc98";
    Key secureKey = Keys.hmacShaKeyFor(secretKey.getBytes());

    String token = Jwts.builder()
      .setSubject("test-user")
      .signWith(secureKey, SignatureAlgorithm.HS256)
      .compact();

    assertNotNull(token);
    assertFalse(token.isEmpty());

    String subject = Jwts.parserBuilder()
      .setSigningKey(secureKey)
      .build()
      .parseClaimsJws(token)
      .getBody()
      .getSubject();

    assertEquals("test-user", subject);
  }
}
