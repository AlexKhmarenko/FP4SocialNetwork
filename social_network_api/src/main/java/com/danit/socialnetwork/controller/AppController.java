package com.danit.socialnetwork.controller;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.security.JwtUserDetails;
import com.danit.socialnetwork.service.DbUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class AppController {
  private final DbUserService userService;

  @GetMapping("api")
  public String handle(Authentication a) {
    JwtUserDetails principal = (JwtUserDetails) a.getPrincipal();
    return String.format("it works: %d", principal.getId());
  }
}
