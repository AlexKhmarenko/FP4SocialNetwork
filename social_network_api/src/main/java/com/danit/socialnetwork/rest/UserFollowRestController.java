package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.UserFollowRequest;
import com.danit.socialnetwork.dto.UserNotificationRequest;
import com.danit.socialnetwork.dto.UserUnfollowRequest;
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

  Boolean usersExist(UserFollowRequest userFollow) {
    Integer follower = userFollow.getUserFollower();
    Integer following = userFollow.getUserFollowing();
    Optional<DbUser> maybeFollower = userRepository.findById(follower);
    Optional<DbUser> maybeFollowing = userRepository.findById(following);

    if (maybeFollower.isEmpty()
        || maybeFollowing.isEmpty()
        || follower == following) {
      return false;
    }
    return true;
  }

  @PostMapping("api/follow")
  public ResponseEntity<?> follow(@RequestBody UserFollowRequest userFollowRequest) {

    if (usersExist(userFollowRequest)) {
      Integer follower = userFollowRequest.getUserFollower();
      Integer following = userFollowRequest.getUserFollowing();
      Optional<UserFollow> maybeUserFollow = userFollowService
          .getUserFollowByUserFollowerIdAndUserFollowingId(follower, following);
      if (maybeUserFollow.isPresent()) {
        return new ResponseEntity<>("Following already exists", HttpStatus.CREATED);
      }
      UserFollow newEntry;
      newEntry = new UserFollow();
      newEntry.setUserFollowerId(follower);
      newEntry.setUserFollowingId(following);
      newEntry.setReceivedNotificationPost(true);
      return new ResponseEntity<>(userFollowService.saveUserFollower(newEntry), HttpStatus.OK);
    }
    return new ResponseEntity<>("invalid user id", HttpStatus.BAD_REQUEST);
  }

  @PostMapping("api/unfollow")
  public ResponseEntity<?> follow(@RequestBody UserUnfollowRequest userUnfollowRequest) {
    Integer unfollowed = userUnfollowRequest.getUserUnfollowed();
    Integer unfollowing = userUnfollowRequest.getUserUnfollowing();

    Optional<UserFollow> deletedUserFollow = userFollowService
        .getUserFollowByUserFollowerIdAndUserFollowingId(unfollowed, unfollowing);

    if (deletedUserFollow.isPresent()) {
      return new ResponseEntity<>(userFollowService
          .deleteUserFollowByUserFollowId(deletedUserFollow.get()
              .getUserFollowId()), HttpStatus.OK);
    }
    return new ResponseEntity<>("invalid user id", HttpStatus.OK);
  }

  @PostMapping("api/notification")
  public ResponseEntity<?> follow(@RequestBody UserNotificationRequest userNotificationRequest) {
    Integer follower = userNotificationRequest.getUserFollower();
    Integer following = userNotificationRequest.getUserFollowing();
    Boolean receivedNotificationPost = userNotificationRequest.getReceiveNotifications();

    Optional<DbUser> maybeFollower = userRepository.findById(follower);
    Optional<DbUser> maybeFollowing = userRepository.findById(following);

    if (maybeFollower.isEmpty()
        || maybeFollowing.isEmpty()
        || follower.equals(following)) {
      return new ResponseEntity<>("invalid user id", HttpStatus.BAD_REQUEST);
    }

    Optional<UserFollow> MaybeUser = userFollowService.getUserFollowByUserFollowerIdAndUserFollowingId(follower, following);

    UserFollow newEntry;
    if (MaybeUser.isPresent()) {
      newEntry = MaybeUser.get();
      newEntry.setReceivedNotificationPost(receivedNotificationPost);
    } else {
      newEntry = new UserFollow();
      newEntry.setUserFollowerId(follower);
      newEntry.setUserFollowingId(following);
      newEntry.setReceivedNotificationPost(true);
    }
    return new ResponseEntity<>(userFollowService.saveUserFollower(newEntry), HttpStatus.OK);
  }
}