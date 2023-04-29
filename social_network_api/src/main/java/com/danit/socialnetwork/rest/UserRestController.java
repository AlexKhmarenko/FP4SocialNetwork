package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.ActivateCodeRequest;
import com.danit.socialnetwork.dto.RegistrationRequest;
import com.danit.socialnetwork.dto.UserEmailRequest;
import com.danit.socialnetwork.dto.UsernameRequest;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserService userService;

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

    Map<String, String> response = new HashMap<>();
    if (userService.save(dbUser)) {
      response.put("registration", "true");
    } else {
      response.put("registration", "false");
    }
    return ResponseEntity.ok(response);
  }

  @RequestMapping(value = "/checkUsername", method = RequestMethod.POST)
  public ResponseEntity<?> handleCheckUsernamePost(
      @RequestBody UsernameRequest request) {

    String username = request.getUsername();
    Map<String, String> response = new HashMap<>();

    if (userService.findByUsername(username).isPresent()) {
      response.put("checkUsername", "true");
    } else {
      response.put("checkUsername", "false");
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

  @RequestMapping(value ="/activate", method = RequestMethod.POST)
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

}
