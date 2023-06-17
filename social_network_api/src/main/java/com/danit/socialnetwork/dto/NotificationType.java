package com.danit.socialnetwork.dto;

public enum NotificationType {
  POST("post"),
  MESSAGE("message");

  private final String notificationType;

  NotificationType(String post) {
    this.notificationType = post;
  }

  public String get() {
    return notificationType;
  }
}
