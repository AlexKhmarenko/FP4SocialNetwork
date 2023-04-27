package com.danit.socialnetwork.restController;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.dto.UsernameRequest;
import com.danit.socialnetwork.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserRestController {

  private final UserServiceImpl userService;

  @PostMapping("registration")
  public ResponseEntity<?> handleRegistrationPost(
      @RequestBody DbUser user) {
    Map<String, String> response = new HashMap<>();

    if (userService.saveUser(user)) {
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

  @GetMapping("/activate/{code}")
  public ResponseEntity<?> activate(@PathVariable String code) {
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
