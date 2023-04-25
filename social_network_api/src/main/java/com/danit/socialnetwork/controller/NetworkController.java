package com.danit.socialnetwork.controller;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.service.DbUserService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;
import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
public class NetworkController {

  private final DbUserService userService;

  //  http://localhost:8080/login
  @GetMapping("login")
  public String showLoginGet() {
    log.info("Hello from login");
    return "login";
  }

  //  http://localhost:8080/registration
  @GetMapping("registration")
  public String showRegistrationGet(Model model) {
    log.info("Hello from registration");
    model.addAttribute("user", new DbUser());
    return "registration";
  }

  @PostMapping("registration")
  public RedirectView handleRegistrationPost(
      @ModelAttribute("user") DbUser user,
      Map<String, String> model) {

    if (!userService.saveUser(user)) {
      model.put("message", "User exists!");
      log.info("User exists!");
      return new RedirectView("registration");
    }
    return new RedirectView("login");
  }

  @GetMapping("/activate/{code}")
  public String activate(Model model, @PathVariable String code) {
    boolean isActivated = userService.activateUser(code);

    if (isActivated) {
      model.addAttribute("message", "User successfully activated");
    } else {
      model.addAttribute("message", "Activation cod is not found");
    }
    return "login";
  }

  //  http://localhost:8080/users
  @GetMapping("/users")
  public String likePageGet() {
    return "likePage";
  }
}
