package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.model.UserFollower;
import com.danit.socialnetwork.service.UserFollowService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserFollowerRestController {

  private final UserFollowService userFollowService;

  @GetMapping("/following/{userId}")
  @ResponseBody
  public List<UserFollower> getAllFollowings(@PathVariable("userId") Integer userId) {
    return userFollowService.getAllUserByUserFollowerId(userId);
  }

  @GetMapping("/followers/{userId}")
  @ResponseBody
  public List<UserFollower> getAllFollowers(@PathVariable("userID") Integer userId) {
    return userFollowService.getAllUserByUserFollowingId(userId);
  }


}
