package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.UserFollow;

import java.util.List;
import java.util.Optional;

public interface UserFollowService {

  List<UserFollow> getAllUserByUserFollowerIdAndReceivedNotificationPost(Integer userFollowerId, boolean notify);

  List<UserFollow> getAllUserByUserFollowerId(Integer userFollowerId);

  List<UserFollow> getAllUserByUserFollowingId(Integer userFollowingId);

  String saveUserFollower(UserFollow userFollow);

  Optional<UserFollow> getUserFollowByUserFollowerIdAndUserFollowingId(
      Integer userFollower, Integer userFollowing);


}