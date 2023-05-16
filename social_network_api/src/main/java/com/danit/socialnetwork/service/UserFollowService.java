package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.UserFollows;

import java.util.List;
import java.util.Optional;

public interface UserFollowService {

  List<UserFollows> getAllUserByUserFollowerIdAndReceivedNotificationPost(Integer userFollowerId);


  List<UserFollows> getAllUserByUserFollowerId(Integer userFollowerId);

  String saveUserFollower (UserFollows userFollows);

  Optional <List<UserFollows>> getUserFollowsByUserFollowerIdIs(
      Integer userFollower);




}
