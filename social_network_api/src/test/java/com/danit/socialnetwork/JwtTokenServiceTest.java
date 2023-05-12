package com.danit.socialnetwork.security;

import com.danit.socialnetwork.security.JwtTokenService;
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
  public void testGenerateToken() {
    String token = jwtTokenService.generateToken(123, true);
    Optional<Jws<Claims>> claims = jwtTokenService.tokenToClaims(token);

    assertTrue(claims.isPresent());
    assertEquals(Optional.of(123), Optional.of(
        jwtTokenService.extractTokenFromClaims(claims.get()).get()));

    long expectedExpiration = System.currentTimeMillis() + jwtTokenService.expirationRemember;

    assertTrue(Math.abs(claims.get().getBody()
        .getExpiration().getTime() - expectedExpiration) < 1000
        && Math.abs(claims.get().getBody()
        .getExpiration().getTime() - expectedExpiration) >= 0);
  }

  @Test
  public void testTokenToClaims() {
    String token = jwtTokenService.generateToken(123, true);
    Optional<Jws<Claims>> claims = jwtTokenService.tokenToClaims(token);

    assertTrue(claims.isPresent());
    assertEquals("123", claims.get().getBody().getSubject());
  }

  @Test
  public void testExtractTokenFromClaims() {
    String token = jwtTokenService.generateToken(123, true);
    Optional<Jws<Claims>> claims = jwtTokenService.tokenToClaims(token);
    Optional<Integer> userId = jwtTokenService.extractTokenFromClaims(claims.get());

    assertTrue(claims.isPresent());
    assertTrue(userId.isPresent());
    assertEquals(Optional.of(123), Optional.of(userId.get()));
  }

//  @Test
//  void testHandle() {
//    JwtUserDetails userDetails = new JwtUserDetails(123, "username", "password", Collections.emptyList());
//    Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, null);
//    JwtTokenService jwtTokenService = new JwtTokenService();
//    assertEquals(123, jwtTokenService.handle(authentication));
//  }

}