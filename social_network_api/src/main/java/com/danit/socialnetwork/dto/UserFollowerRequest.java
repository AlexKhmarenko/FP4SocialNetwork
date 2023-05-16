package com.danit.socialnetwork.dto;

import lombok.Data;

@Data
public class UserFollowerRequest {
  Integer userFollower;
  Integer userFollowing;
  Boolean receiveNotifications;
}
