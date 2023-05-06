package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.controller.PasswordChanger;
import com.danit.socialnetwork.model.PasswordChangeRequests;
import com.danit.socialnetwork.service.PasswordChangerService;
import com.danit.socialnetwork.service.UserService;
import com.danit.socialnetwork.dto.ActivateCodeRequest;
import com.danit.socialnetwork.dto.RegistrationRequest;
import com.danit.socialnetwork.dto.UserEmailRequest;
import com.danit.socialnetwork.dto.UsernameRequest;
import com.danit.socialnetwork.model.DbUser;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
//import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.core.OAuth2AccessToken;


import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;
  PasswordChanger passChanger = new PasswordChanger();
  private final PasswordChangerService passwordChangerService;

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

  @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
  public ResponseEntity<?> handleCheckUsernamePost(
      @RequestBody UsernameRequest request) throws IOException {

    String username = request.getUsername();
    Map<String, String> response = new HashMap<>();

    if (userService.findByUsername(username) == null) {
      response.put("checkUsername", "false");
    } else {
      response.put("checkUsername", "true");
    }
    return ResponseEntity.ok(response);
  }

  @RequestMapping(value = "/sendLetter", method = RequestMethod.POST)
  public ResponseEntity<?> handleSendLetterPost(
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

  @GetMapping("/@{username}")
  public Optional<DbUser> getUser(@PathVariable("username") String username) throws IOException {
    return userService.findByUsername(username);
  }

  @GetMapping(value = "/@{username}/photo", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getProfileImage(@PathVariable("username") String username) throws IOException {
    return userService.getProfileImage(username);
  }

  @GetMapping(value = "/@{username}/header_photo", produces = MediaType.IMAGE_PNG_VALUE)
  @ResponseBody
  public byte[] getBackgroundImage(@PathVariable("username") String username) throws IOException {
    return userService.getBackgroundImage(username);
  }

  @RequiredArgsConstructor
  @Data
  private static class UserEmail {
    private final String email;
  }

  @PostMapping("/changepassword")
  public ResponseEntity<String> changePass(@RequestBody Map<String, String> loginData,
                                           HttpServletRequest request) {
    String userEmail = loginData.get("email");
    String secretUrl = passChanger.change(request, userEmail);
    log.info(passwordChangerService.saveRequest(userEmail, secretUrl));
    return ResponseEntity.ok("Instructions sent on, " + userEmail);
  }

  @GetMapping("/changepassword{uuid}")
  public ResponseEntity<UserEmail> res(@PathVariable("uuid") String uuid) {

    if (passwordChangerService.getEmailByUuid(uuid).isPresent()) {
      PasswordChangeRequests maybeRequest = passwordChangerService.getEmailByUuid(uuid).get();
      UserEmail email = new UserEmail(maybeRequest.getEmail());
      log.info("Change password page call from e-mail " + email.getEmail());
      passwordChangerService.deleteRequestByEmail(maybeRequest.getEmail());
      return new ResponseEntity<>(email, HttpStatus.OK);
    } else {
      log.info("Invalid Change password page link from e-mail");
      return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping("/newpassword")
  public ResponseEntity<String> authenticateUser(@RequestBody Map<String, String> loginData) {
    String username = loginData.get("username");
    String password = loginData.get("password");

    ///encode and wright new pass

    return ResponseEntity.ok("Welcome, " + username + "!");
  }


}