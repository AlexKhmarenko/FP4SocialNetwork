package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.model.DbUser;

import com.danit.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@Log4j2
@RequiredArgsConstructor
public class UserRestController {
  private final UserService userService;
  @GetMapping("/{username}")
  public DbUser getUser(@PathVariable("username") String username) {
    return userService.findByUsername(username);
  }

  @GetMapping(value = "/{username}/photo", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getProfileImage(@PathVariable("username") String username) throws IOException {
    return userService.getProfileImage(username);
  }

  @GetMapping(value = "/{username}/header_photo", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getBackgroundImage(@PathVariable("username") String username) throws IOException {
    return userService.getBackgroundImage(username);
  }


}
