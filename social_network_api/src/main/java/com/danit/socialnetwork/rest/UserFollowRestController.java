package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.UserFollowerRequest;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.UserFollow;
import com.danit.socialnetwork.repository.UserRepository;
import com.danit.socialnetwork.service.UserFollowServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserFollowRestController {

  private final UserFollowServiceImpl userFollowService;
  private final UserRepository userRepository;

  @GetMapping("/following/{userID}")
  @ResponseBody
  public List<UserFollow> getAllFollowing(@PathVariable("userID") Integer userId) {

    return userFollowService.getAllUserByUserFollowerId(userId);
  }

  @PostMapping("api/follow")
  public ResponseEntity<?> follow(@RequestBody UserFollowerRequest userFollow) {
    Integer follower = userFollow.getUserFollower();
    Integer following = userFollow.getUserFollowing();
    Boolean receivedNotificationPost = userFollow.getReceiveNotifications();

    Optional<DbUser> maybeFollower = userRepository.findById(follower);
    Optional<DbUser> maybeFollowing = userRepository.findById(following);

    if (maybeFollower.isEmpty()
        || maybeFollowing.isEmpty()
        || follower.equals(following)) {
      return new ResponseEntity<>("invalid user id", HttpStatus.BAD_REQUEST);
    }

    Optional<UserFollow> dbEntry = userFollowService.getUserFollowByUserFollowerIdAndUserFollowingId(follower, following);

    UserFollow newEntry;
    if (dbEntry.isPresent()) {
      newEntry = dbEntry.get();
    } else {
      newEntry = new UserFollow();
      newEntry.setUserFollowerId(follower);
      newEntry.setUserFollowingId(following);
    }
    newEntry.setReceivedNotificationPost(receivedNotificationPost);
    return new ResponseEntity<>(userFollowService.saveUserFollower(newEntry), HttpStatus.OK);
  }
}