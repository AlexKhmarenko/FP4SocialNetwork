package com.danit.socialnetwork.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class NotificationRequest {
  Integer userId;
  String notificationText;
}