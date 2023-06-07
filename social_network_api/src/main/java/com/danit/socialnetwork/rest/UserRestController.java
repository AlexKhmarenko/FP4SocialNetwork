package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.ActivateCodeRequest;
import com.danit.socialnetwork.dto.RegistrationRequest;
import com.danit.socialnetwork.dto.UserDobChangeRequest;
import com.danit.socialnetwork.dto.UserEmailForLoginRequest;
import com.danit.socialnetwork.dto.UserEmailRequest;
import com.danit.socialnetwork.dto.user.UserDtoForPostLikeResponse;
import com.danit.socialnetwork.dto.search.SearchDto;
import com.danit.socialnetwork.dto.search.SearchRequest;
import com.danit.socialnetwork.dto.user.EditingDtoRequest;
import com.danit.socialnetwork.dto.user.UserDtoForSidebar;
import com.danit.socialnetwork.dto.user.UserDtoResponse;
import com.danit.socialnetwork.mappers.SearchMapper;
import com.danit.socialnetwork.service.UserService;
import com.danit.socialnetwork.model.DbUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.io.IOException;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserRestController {
  private static final String FALSE = "false";
  private static final String TRUE = "true";

  private final UserService userService;

  private final SearchMapper searchMapper;

  @PostMapping(path = "registration")
  public ResponseEntity<Map<String, String>> handleRegistrationPost(
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
      response.put("registration", TRUE);
      return ResponseEntity.ok(response);
    } else {
      response.put("registration", FALSE);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "/checkEmail")
  public ResponseEntity<Map<String, String>> handleCheckEmailPost(
      @RequestBody UserEmailForLoginRequest request) throws IOException {

    String email = request.getEmail();
    Map<String, String> response = new HashMap<>();

    if (userService.findDbUserByEmail(email) == null) {
      response.put("checkEmail", FALSE);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    } else {
      response.put("checkEmail", TRUE);
      return new ResponseEntity<>(response, HttpStatus.FOUND);

    }
  }

  @PostMapping(value = "/sendLetter")
  public ResponseEntity<Map<String, String>> handleSendLetterPost(
      @RequestBody UserEmailRequest request) {

    Map<String, String> response = new HashMap<>();
    String name = request.getName();
    String email = request.getEmail();

    if (userService.sendLetter(name, email)) {
      response.put("sendLetter", TRUE);
      return ResponseEntity.ok(response);
    } else {
      response.put("sendLetter", FALSE);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @PostMapping(value = "/activate")
  public ResponseEntity<Map<String, String>> handleActivatePost(
      @RequestBody ActivateCodeRequest request) {
    Integer code = request.getCode();
    boolean isActivated = userService.activateUser(code);
    Map<String, String> response = new HashMap<>();

    if (isActivated) {
      response.put("activate", TRUE);
      return ResponseEntity.ok(response);
    } else {
      response.put("activate", FALSE);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }

  @GetMapping(value = "/api/search")
  public ResponseEntity<List<SearchDto>> handleSearchPost(@RequestBody SearchRequest request) {
    List<SearchDto> searchDto = userService.filterCachedUsersByName(request);
    log.debug(String.format("filterCachedUsersByName: %s. Find all users by name.",
        request.getSearch()));
    return new ResponseEntity<>(searchDto, HttpStatus.FOUND);
  }

  @PutMapping(value = "/edition")
  public ResponseEntity<Map<String, String>> handleEditionPost(
      @RequestBody EditingDtoRequest request) {
    Map<String, String> response = new HashMap<>();
    if (userService.update(request)) {
      response.put("edition", TRUE);
      return ResponseEntity.ok(response);
    } else {
      response.put("edition", FALSE);
      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  }


  @GetMapping("/api/profile/{userId}")
  public ResponseEntity<UserDtoResponse> getUserById(@PathVariable("userId") Integer userId) {
    UserDtoResponse tempUser = userService.findByUserId(userId);
    return new ResponseEntity<>(tempUser, HttpStatus.OK);
  }

  @PostMapping("/api/change_dob")
  public ResponseEntity<Map<String, String>> dbUserDobChange(
      @RequestBody UserDobChangeRequest userDobChangeRequest) {
    return userService.dbUserDobChange(userDobChangeRequest);
  }

  @GetMapping("/api/users/likes")
  public List<UserDtoForPostLikeResponse> getUsersWhoLikedPostByPostId(@RequestParam(name = "postId",
      defaultValue = "0") Integer postId, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    if (postId == 0) {
      return new ArrayList<>();
    }
    return userService.getUsersWhoLikedPostByPostId(postId, page)
        .stream()
        .map(UserDtoForPostLikeResponse::from)
        .toList();
  }


  @GetMapping("/api/users/popular")
  public List<UserDtoForSidebar> getUsersWhoMostPopular(@RequestParam(name = "page", defaultValue = "0") Integer page) {
    return userService.getUsersWhoMostPopular(page)
        .stream()
        .map(UserDtoForSidebar::from)
        .toList();
  }


}