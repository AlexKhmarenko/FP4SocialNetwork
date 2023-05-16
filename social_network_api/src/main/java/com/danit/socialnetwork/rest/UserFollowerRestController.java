package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.model.UserFollows;
import com.danit.socialnetwork.repository.UserFollowRepository;
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
public class UserFollowerRestController {

  private final UserFollowServiceImpl userFollowService;
  private final UserFollowRepository userFollowRepository;

  @GetMapping("/following/{userID}")
  @ResponseBody
  public List<UserFollows> getAllFollowing(@PathVariable("userID") Integer userId) {

    return userFollowService.getAllUserByUserFollowerId(userId);
  }

  @PostMapping("api/follow")
  public ResponseEntity<?> follow(@RequestBody UserFollows userFollows) {
    Integer follower = userFollows.getUserFollowerId();
    Integer following = userFollows.getUserFollowingId();
    Boolean receivedNotificationPost = userFollows.getReceivedNotificationPost();

    System.out.println(follower);
    System.out.println(following);
    System.out.println(receivedNotificationPost);

    Optional <List<UserFollows>> dbEntry = userFollowService.getUserFollowsByUserFollowerIdIs(follower);

    System.out.println(dbEntry);

    UserFollows newEntry;
    if (dbEntry.isPresent()) {
      System.out.println(dbEntry.get());
      newEntry = dbEntry.get().get(0);
    }
    else{
      newEntry = new UserFollows();
      newEntry.setUserFollowerId(follower);
      newEntry.setUserFollowingId(following);
    }
    newEntry.setReceivedNotificationPost(receivedNotificationPost);
    return new ResponseEntity<>(userFollowService.saveUserFollower(newEntry), HttpStatus.OK);
  }
}



//    String userEmail = codeCheckRequest.getEmail();
//    String secretCode = codeCheckRequest.getCode();
//    Optional<PasswordChangeRequests> maybeRequest = passwordChangerService.getEmailByUuid(secretCode);
//
//    if (maybeRequest.isPresent()) {
//      PasswordRestController.UserEmail email = new PasswordRestController.UserEmail(maybeRequest.get().getEmail());
//      if (email.getEmail().equals(userEmail)) {
//        log.info("Change password page call from e-mail " + email.getEmail());
//        passwordChangerService.deleteRequestByEmail(maybeRequest.get().getEmail());
//        return new ResponseEntity<>("code accessed", HttpStatus.OK);
//      }
//    }
//    log.info("Invalid code");
//    return new ResponseEntity<>("Invalid code",
//        HttpStatus.BAD_REQUEST);
//  }

//}
