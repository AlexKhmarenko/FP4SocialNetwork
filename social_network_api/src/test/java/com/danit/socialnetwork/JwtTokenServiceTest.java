package com.danit.socialnetwork.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JwtTokenServiceTest extends TestCase {

  @Autowired
  JwtTokenService jwtTokenService = new JwtTokenService();

  @Test
  public void testExtractTokenFromClaims() {
    String token = jwtTokenService.generateToken(123, true);
    Optional<Jws<Claims>> claims = jwtTokenService.tokenToClaims(token);
    Optional<Integer> userId = jwtTokenService.extractTokenFromClaims(claims.get());

    assertTrue(claims.isPresent());
    assertTrue(userId.isPresent());
    assertEquals(Optional.of(123), Optional.of(userId.get()));
  }

}