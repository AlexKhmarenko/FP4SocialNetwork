package com.danit.socialnetwork.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostNotificationRequest {
  LocalDateTime dateTime;
  Integer userId;
  Integer postId;
  String userName;
  String userPhoto;
  String notificationText;
  Boolean notificationRead;
}
