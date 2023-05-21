package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.controller.PasswordChanger;
import com.danit.socialnetwork.dto.UserEmailForLoginRequest;
import com.danit.socialnetwork.dto.UserEmailRequest;
import com.danit.socialnetwork.dto.ActivateCodeRequest;
import com.danit.socialnetwork.dto.SearchRequest;
import com.danit.socialnetwork.dto.RegistrationRequest;
import com.danit.socialnetwork.service.PasswordChangerServiceImpl;
import com.danit.socialnetwork.service.UserService;
import com.danit.socialnetwork.model.DbUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;
  PasswordChanger passChanger = new PasswordChanger();
  private final PasswordChangerServiceImpl passwordChangerService;

  @RequestMapping(value = "registration", method = RequestMethod.POST)
  public ResponseEntity<?> handleRegistrationPost(
      @RequestBody RegistrationRequest request) {
    int day = request.getDay();
    int month = request.getMonth();
    int year = request.getYear();
    LocalDate dateOfBirth = LocalDate.of(year, month, day);

    DbUser dbUser = new DbUser();
    dbUser.setUsername(request.getUsername());
    dbUser.setPassword(request.getPassword());
    dbUser.setEmail(request.getEmail());
    dbUser.setName(request.getName());
    dbUser.setDateOfBirth(dateOfBirth);

    Map<String, Boolean> response = new HashMap<>();
    response.put("registration", userService.save(dbUser));
    return ResponseEntity.ok(response);
  }

  @PostMapping(value = "/checkEmail")
  public ResponseEntity<?> handleCheckUsernamePost(
      @RequestBody UserEmailForLoginRequest request) throws IOException {

    String email = request.getEmail();
    Map<String, String> response = new HashMap<>();

    if (userService.findDbUserByEmail(email) == null) {
      response.put("checkEmail", "false");
    } else {
      response.put("checkEmail", "true");
    }
    return ResponseEntity.ok(response);
  }

  @RequestMapping(value = "/sendLetter", method = RequestMethod.POST)
  public ResponseEntity handleSendLetterPost(
      @RequestBody UserEmailRequest request) {

    Map<String, String> response = new HashMap<>();
    String name = request.getName();
    String email = request.getEmail();

    if (userService.sendLetter(name, email)) {
      response.put("sendLetter", "true");
    } else {
      response.put("sendLetter", "false");
    }
    return ResponseEntity.ok(response);
  }

  @RequestMapping(value = "/activate", method = RequestMethod.POST)
  public ResponseEntity<?> handleActivatePost(
      @RequestBody ActivateCodeRequest request) {
    Integer code = request.getCode();
    boolean isActivated = userService.activateUser(code);
    Map<String, String> response = new HashMap<>();

    if (isActivated) {
      response.put("activate", "true");
    } else {
      response.put("activate", "false");
    }
    return ResponseEntity.ok(response);
  }

  @RequestMapping(value = "/search", method = RequestMethod.POST)
  public ResponseEntity<?> handleSearchPost(
      @RequestBody SearchRequest request) {
    String userSearch = request.getUserSearch();
    List<DbUser> search = userService.filterCachedUsersByName(userSearch);
    Map<String, List<DbUser>> response = new HashMap<>();
    response.put("search", search);

    return ResponseEntity.ok(response);
  }

  @GetMapping("/{username}")
  public Optional<DbUser> getUser(@PathVariable("username") String username) throws IOException {
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