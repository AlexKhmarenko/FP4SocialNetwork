package com.danit.socialnetwork.websocket;

import com.danit.socialnetwork.dto.NotificationType;
import com.danit.socialnetwork.dto.NotificationRequest;
import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.post.RepostDtoSave;
import com.danit.socialnetwork.dto.user.UserDtoResponse;
import com.danit.socialnetwork.dto.user.UserFollowDtoResponse;
import com.danit.socialnetwork.mappers.InboxMapperImpl;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Notification;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.service.UserService;
import com.danit.socialnetwork.service.UserFollowService;
import com.danit.socialnetwork.service.NotificationService;
import com.danit.socialnetwork.service.MessageService;
import com.danit.socialnetwork.service.InboxService;
import com.danit.socialnetwork.service.PostService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@Controller
@RequiredArgsConstructor
@Data
public class WebSocketController {
  private final NotificationService notificationService;
  private final UserFollowService userFollowService;
  private final UserService userService;
  private final PostService postService;
  private final InboxService inboxService;
  private final MessageService messageService;
  private final InboxMapperImpl mapper;

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/post")
  public NotificationRequest postNotification(
      @Payload NotificationRequest notificationRequest) throws InterruptedException {

    Thread.sleep(500);

    UserDtoResponse user = userService.findByUserId(notificationRequest.getUserId());

    LocalDateTime dateTime = LocalDateTime.now();
    notificationRequest.setDateTime(dateTime);

    Integer userId = user.getUserId();
    Integer postId = postService.findLatestPostIdByUserId(userId);
    notificationRequest.setEventId(postId);
    log.info("POST ID: " + postId);

    String notificationType = NotificationType.POST.get();
    notificationRequest.setEventType(notificationType);

    String userName = user.getUsername();
    notificationRequest.setUserName(userName);

    String userPhoto = user.getProfileImageLink();
    notificationRequest.setUserPhoto(userPhoto);

    String notificationText = userName + " published a new post";
    notificationRequest.setNotificationText(notificationText);

    notificationRequest.setNotificationRead(false);

    List<UserFollowDtoResponse> followers = userFollowService
        .getAllUsersByUserFollowerId(userId);

    for (UserFollowDtoResponse follower : followers) {
      Integer followerId = follower.getUserId();

      Notification newNotification = new Notification(
          followerId, notificationType, notificationRequest.getEventId(), notificationRequest.getUserId(),
          userName, userPhoto, notificationText);
      notificationService.saveNotification(newNotification);

      String followerIdString = followerId.toString();
      messagingTemplate.convertAndSendToUser(followerIdString, "/notifications", notificationRequest);

      int unreadNotificationsNum = notificationService
          .findAllByFollowerUserIdAndNotificationRead(followerId, false).size();
      Map<String, Integer> unreadNotifications = new HashMap<>();
      unreadNotifications.put("unreadNotifications", unreadNotificationsNum);
      messagingTemplate.convertAndSendToUser(followerIdString, "/unread_notifications", unreadNotifications);
    }
    return notificationRequest;
  }

  @MessageMapping("/repost")
  public NotificationRequest postNotification(
      @Payload RepostDtoSave repostDtoSave) {
    Integer repostUserId = repostDtoSave.getUserId();
    Integer postId = repostDtoSave.getPostId();

    Post postByPostId = postService.findPostByPostId(postId);

    Integer authUserId = postByPostId.getUserPost().getUserId();

    DbUser repostUser = userService.findDbUserByUserId(repostUserId);

    String notificationType = NotificationType.POST.get();

    NotificationRequest notificationRequest = new NotificationRequest();

    LocalDateTime dateTime = LocalDateTime.now();
    notificationRequest.setDateTime(dateTime);

    notificationRequest.setUserId(authUserId);

    notificationRequest.setEventType(notificationType);
    notificationRequest.setEventId(postId);

    String repostUserUsername = repostUser.getUsername();
    notificationRequest.setUserName(repostUserUsername);

    String repostUserPhoto = repostUser.getProfileImageUrl();
    notificationRequest.setUserPhoto(repostUserPhoto);

    String notificationText = repostUserUsername + " reposted your post";
    notificationRequest.setNotificationText(notificationText);

    notificationRequest.setNotificationRead(false);

    Notification newNotification = new Notification(
        authUserId, notificationType, postId, repostUserId,
        repostUserUsername, repostUserPhoto, notificationText);

    notificationService.saveNotification(newNotification);

    String authUserIdString = authUserId.toString();
    messagingTemplate.convertAndSendToUser(authUserIdString, "/notifications", notificationRequest);

    int unreadNotificationsNum = notificationService
        .findAllByFollowerUserIdAndNotificationRead(authUserId, false).size();
    Map<String, Integer> unreadNotifications = new HashMap<>();
    unreadNotifications.put("unreadNotifications", unreadNotificationsNum);
    messagingTemplate.convertAndSendToUser(authUserIdString, "/unread_notifications", unreadNotifications);

    return notificationRequest;
  }

  @MessageMapping("/post_like")
  public NotificationRequest postLikeNotification(
      @Payload RepostDtoSave repostDtoSave) {
    Integer likerUser = repostDtoSave.getUserId();
    Integer postId = repostDtoSave.getPostId();

    Post postByPostId = postService.findPostByPostId(postId);

    Integer authUserId = postByPostId.getUserPost().getUserId();

    DbUser likerDbUser = userService.findDbUserByUserId(likerUser);

    String notificationType = NotificationType.POST.get();

    NotificationRequest notificationRequest = new NotificationRequest();

    LocalDateTime dateTime = LocalDateTime.now();
    notificationRequest.setDateTime(dateTime);

    notificationRequest.setUserId(authUserId);

    notificationRequest.setEventType(notificationType);
    notificationRequest.setEventId(postId);

    String likerUserUsername = likerDbUser.getUsername();
    notificationRequest.setUserName(likerUserUsername);

    String likerUserPhoto = likerDbUser.getProfileImageUrl();
    notificationRequest.setUserPhoto(likerUserPhoto);

    String notificationText = likerUserUsername + " likes your post";
    notificationRequest.setNotificationText(notificationText);

    notificationRequest.setNotificationRead(false);

    Notification newNotification = new Notification(
        authUserId, notificationType, postId, likerUser,
        likerUserUsername, likerUserPhoto, notificationText);

    notificationService.saveNotification(newNotification);

    String authUserIdString = authUserId.toString();
    messagingTemplate.convertAndSendToUser(authUserIdString, "/notifications", notificationRequest);

    int unreadNotificationsNum = notificationService
        .findAllByFollowerUserIdAndNotificationRead(authUserId, false).size();
    Map<String, Integer> unreadNotifications = new HashMap<>();
    unreadNotifications.put("unreadNotifications", unreadNotificationsNum);
    messagingTemplate.convertAndSendToUser(authUserIdString, "/unread_notifications", unreadNotifications);

    return notificationRequest;
  }

  @MessageMapping("/addMessage")
  public InboxDtoResponse postAddMessage(
      @Payload MessageDtoRequest messageDtoRequest) {

    Integer inboxUid = messageDtoRequest.getInboxUid();
    Integer userId = messageDtoRequest.getUserId();
    log.info("inboxUid {}", inboxUid);
    log.info("userId {}", userId);

    List<InboxDtoResponse> inboxesSender = inboxService.getInboxesByInboxUid(inboxUid);
    List<InboxDtoResponse> inboxesReceiver = inboxService.getInboxesByInboxUid(userId);

    InboxDtoResponse inboxSender = inboxesSender.stream().filter(i -> i.getUserId().equals(userId)).toList().get(0);
    InboxDtoResponse inboxReceiver = inboxesReceiver.stream().filter(i -> i.getUserId().equals(inboxUid)).toList().get(0);

    // inboxReceiver was instantiated using the method 'getInboxesByInboxUid',
    // and the comment of that method says: "The method finds the inbox by SENDER and returns it".
    // The problem is that the RECEIVER is NOT THE SENDER. The userId and inboxUid properties were wrongly placed
    // inside the 'getInboxesByInboxUid' method for the receiver, while staying correct for the sender
    // (because method is expected to work with the sender).
    // Now, there are 2 options here:
    // 1) refactor 'getInboxesByInboxUid' method or split it into 2 separate method, to be able to work separately with
    // SENDER and RECEIVER.
    // 2) update the inboxReceiver object with proper inboxUid and userId properties (because other properties are already set correctly).
    // I'll use the second one as it's faster and will work.
    inboxReceiver.setUserId(userId);
    inboxReceiver.setInboxUid(inboxUid);

    log.info("inboxSender {}", inboxSender);
    log.info("inboxReceiver {}", inboxReceiver);

    int unreadMessagesNum = messageService
        .numberUnreadMessages(inboxUid);
    Map<String, Integer> unreadMessages = new HashMap<>();
    unreadMessages.put("unread", unreadMessagesNum);

    String userIdString = userId.toString();
    messagingTemplate.convertAndSendToUser(userIdString, "/unread", unreadMessages);

    int unreadMessagesByUserNumSender = messageService
        .numberUnreadMessagesByUser(userId, inboxUid);
    Map<String, Integer> unreadMessagesByUser = new HashMap<>();
    unreadMessagesByUser.put("unread", unreadMessagesByUserNumSender);
    inboxSender.setUnreadByUser(unreadMessagesByUserNumSender);

    String inboxUidString = inboxUid.toString();
    messagingTemplate.convertAndSendToUser(inboxUidString, "/inbox", inboxSender);
    messagingTemplate.convertAndSendToUser(userIdString, "/inbox", inboxReceiver);

    messagingTemplate.convertAndSendToUser(inboxUidString, "/getMessages", inboxSender);
    messagingTemplate.convertAndSendToUser(userIdString, "/getMessages", inboxReceiver);
    return inboxSender;
  }
}