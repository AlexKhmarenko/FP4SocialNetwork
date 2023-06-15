package com.danit.socialnetwork.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationRequest {
  Integer userId;
  Integer eventId;
  String userName;
  String notificationText;
  String userPhoto;
  LocalDateTime dateTime;
  Boolean notificationRead;
}
