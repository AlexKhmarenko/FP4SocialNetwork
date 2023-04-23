package com.danit.socialnetwork;

import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Log4j2
@Service
@PropertySource("classpath:jwt.properties")
public class JwtTokenService {

  @Value("${jwt.secret}")
  private String jwtSecret = "eyJhbGciOiJIUzI1NiJ9.eyJSb2xlIjoiQWRtaW4iLCJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6IkphdmFJblVzZSIsImV4cCI6MTY4MDExMzM3NywiaWF0IjoxNjgwMTEzMzc3fQ.jFPQ9JlHR4DQQtforpePZziCmr4133-9JLYB_8noMp4";

  @Value("${jwt.expire.normal}")
  private Long expiration_normal = 60 * 60 * 24 * 1000L; // 1d

  @Value("${jwt.expire.remember}")
  Long expiration_remember = 60 * 60 * 24 * 1000L * 10; // 10d

  public String generateToken(String username, String password, boolean rememberMe) {
    String subject = username + "+" + password;
    Date now = new Date();
    Date expiry = new Date(now.getTime() + (rememberMe ? expiration_remember : expiration_normal));
    String token = Jwts.builder()
        .setSubject(subject.toString())
        .setIssuedAt(now)
        .setExpiration(expiry)
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
    log.info("Token " + token);

    return token;
  }

  public Optional<Jws<Claims>> tokenToClaims(String token) {
    try {
      return Optional.of(Jwts.parser()
          .setSigningKey(jwtSecret)
          .parseClaimsJws(token));
    } catch (SignatureException x) {
      log.error(getClass() + " SignatureException");
    } catch (MalformedJwtException x) {
      log.error(getClass() + " MalformedJwtException");
    } catch (ExpiredJwtException x) {
      log.error(getClass() + " ExpiredJwtException");
    } catch (UnsupportedJwtException x) {
      log.error(getClass() + " UnsupportedJwtException");
    } catch (Exception x) {
      log.error(getClass() + " Exception");
    }
    return Optional.empty();
  }

  public Optional<Integer> extractTokenFromClaims(Jws<Claims> claims) {
    try {
      return Optional
          .of(claims.getBody().getSubject())
          .map(Integer::parseInt);
    } catch (Exception x) {
      log.error(getClass() + " Exception");
      return Optional.empty();
    }
  }

  // https://jwt.io
  public static void main(String[] args) {
    JwtTokenService ts = new JwtTokenService();
    String t = ts.generateToken("Nadya", "123",false);

    Optional<Integer> maybeUsernamePassword = ts.tokenToClaims(t)
        .flatMap(ts::extractTokenFromClaims);

    System.out.println(t);
    System.out.println(maybeUsernamePassword);
  }
}
