package com.danit.socialnetwork.controller;

import com.danit.socialnetwork.security.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppController {

  @GetMapping("api")
  public String handle(Authentication a) {
    JwtUserDetails principal = (JwtUserDetails) a.getPrincipal();
    return String.format("it works: %d", principal.getId());
  }
}
