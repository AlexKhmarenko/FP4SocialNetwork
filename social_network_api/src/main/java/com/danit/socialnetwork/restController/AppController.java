package com.danit.socialnetwork.restController;

import com.danit.socialnetwork.security.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AppController {

  @GetMapping("api")
  public String handle(Authentication a) {
    JwtUserDetails principal = (JwtUserDetails) a.getPrincipal();
    return String.format("it works: %d", principal.getUsername());
  }
}
