package com.danit.socialnetwork.websocket;

import com.danit.socialnetwork.dto.NotificationType;
import com.danit.socialnetwork.dto.NotificationRequest;
import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.MessageRequest;
import com.danit.socialnetwork.dto.post.RepostDtoSave;
import com.danit.socialnetwork.dto.user.UserDtoResponse;
import com.danit.socialnetwork.dto.user.UserFollowDtoResponse;
import com.danit.socialnetwork.mappers.InboxMapperImpl;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Inbox;
import com.danit.socialnetwork.model.Notification;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.service.InboxService;
import com.danit.socialnetwork.service.NotificationService;
import com.danit.socialnetwork.service.PostService;
import com.danit.socialnetwork.service.UserFollowService;
import com.danit.socialnetwork.service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Controller
@RequiredArgsConstructor
@Data
public class WebSocketController {
  private final NotificationService notificationService;
  private final UserFollowService userFollowService;
  private final UserService userService;
  private final InboxService inboxService;
  private final PostService postService;
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
      @Payload RepostDtoSave repostDtoSave)  {
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
      @Payload InboxDtoResponse inboxDtoResponse) throws InterruptedException {

    Thread.sleep(500);
    Integer inboxUid = inboxDtoResponse.getInboxUid();
    Integer userId = inboxDtoResponse.getUserId();
    DbUser userReceiver = userService.findDbUserByUserId(inboxDtoResponse.getUserId());
    DbUser userSender = userService.findDbUserByUserId(inboxDtoResponse.getUserId());

    Optional<Inbox> inboxSender = inboxService.findByInboxUidAndLastSentUserId(userSender, userReceiver);
    Optional<Inbox> inboxesReceiver = inboxService.findByInboxUidAndLastSentUserId(userReceiver, userSender);

    String inboxUidString = inboxUid.toString();
    String inboxUserIdString = userId.toString();
    InboxDtoResponse inboxesSenderDto = mapper.inboxToInboxDtoResponse(inboxSender.get());
    InboxDtoResponse inboxesReceiverDto = mapper.inboxToInboxDtoResponse(inboxesReceiver.get());
    messagingTemplate.convertAndSendToUser(inboxUidString, "/inbox", inboxesSenderDto);
    messagingTemplate.convertAndSendToUser(inboxUserIdString, "/inbox", inboxesReceiverDto);

    MessageDtoResponse lastMessageSenderDto = new MessageDtoResponse();
    lastMessageSenderDto.setInboxUid(inboxesSenderDto.getInboxUid());
    lastMessageSenderDto.setUserId(inboxesSenderDto.getUserId());
    lastMessageSenderDto.setMessage(inboxesSenderDto.getMessage());
    lastMessageSenderDto.setCreatedAt(inboxesSenderDto.getCreatedAt());

    MessageDtoResponse lastMessageReceiverDto = new MessageDtoResponse();
    lastMessageReceiverDto.setInboxUid(inboxesReceiverDto.getInboxUid());
    lastMessageReceiverDto.setUserId(inboxesReceiverDto.getUserId());
    lastMessageReceiverDto.setMessage(inboxesReceiverDto.getMessage());
    lastMessageReceiverDto.setCreatedAt(inboxesReceiverDto.getCreatedAt());

    messagingTemplate.convertAndSendToUser(inboxUidString, "/getMessages", lastMessageSenderDto);
    messagingTemplate.convertAndSendToUser(inboxUserIdString, "/getMessages", lastMessageReceiverDto);
    return inboxesReceiverDto;
  }
}