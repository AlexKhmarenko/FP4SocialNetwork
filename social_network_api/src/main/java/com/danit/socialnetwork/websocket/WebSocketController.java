package com.danit.socialnetwork.websocket;

import com.danit.socialnetwork.dto.NotificationType;
import com.danit.socialnetwork.dto.PostNotificationRequest;
import com.danit.socialnetwork.dto.user.UserDtoResponse;
import com.danit.socialnetwork.dto.user.UserFollowDtoResponse;
import com.danit.socialnetwork.model.Notification;
import com.danit.socialnetwork.service.NotificationService;
import com.danit.socialnetwork.service.PostService;
import com.danit.socialnetwork.service.UserFollowService;
import com.danit.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;

@Log4j2
@Controller
@RequiredArgsConstructor
public class WebSocketController {
  private final NotificationService notificationService;
  private final UserFollowService userFollowService;
  private final UserService userService;

  private final PostService postService;
  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/post")
  public PostNotificationRequest postNotification(
      @Payload PostNotificationRequest postNotificationRequest) throws InterruptedException {
    Thread.sleep(500);

    UserDtoResponse user = userService.findByUserId(postNotificationRequest.getUserId());
    Integer userId = user.getUserId();
    String userName = user.getUsername();
    String userPhoto = user.getProfileImageLink();
    LocalDateTime dateTime = LocalDateTime.now();
    String notificationText = userName + " published a new post";

    Integer postId = postService.findLatestPostIdByUserId(userId);
    log.info("POST ID: " + postId);

    postNotificationRequest.setNotificationText(notificationText);
    postNotificationRequest.setUserName(userName);
    postNotificationRequest.setUserPhoto(userPhoto);
    postNotificationRequest.setPostId(postId);
    postNotificationRequest.setDateTime(dateTime);
    postNotificationRequest.setNotificationRead(false);


    List<UserFollowDtoResponse> followers = userFollowService
        .getAllUsersByUserFollowerId(userId);

    List<Notification> allByFollowingUserId = notificationService.findAllByUserId(userId);
    System.out.println(allByFollowingUserId);

    for (UserFollowDtoResponse follower : followers) {
      Integer followerId = follower.getUserId();
      String notificationType = NotificationType.POST.get();
      Notification newNotification = new Notification(
          followerId, notificationType, postNotificationRequest.getPostId(), postNotificationRequest.getUserId(),
          userPhoto, notificationText);
      notificationService.saveNotification(newNotification);



      String followerIdString = followerId.toString();
      messagingTemplate.convertAndSendToUser(followerIdString, "/notifications", postNotificationRequest);
    }
    return postNotificationRequest;
  }
}