package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.UserFollows;
import com.danit.socialnetwork.repository.UserFollowRepository;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserFollowServiceImpl implements UserFollowService {

  private final UserFollowRepository userFollowRepository;
  private final UserRepository userRepository;

  @Override
  public List<UserFollows> getAllUserByUserFollowerIdAndReceivedNotificationPost(Integer userFollowerId) {
    return userFollowRepository
        .findAllByUserFollowerIdAndReceivedNotificationPostContaining(
            userFollowerId, true);
  }

  @Override
  public List<UserFollows> getAllUserByUserFollowerId(Integer userFollowerId) {
    return userFollowRepository
        .findAllByUserFollowerId(userFollowerId);
//        .findAllByUserFollowerId(userRepository.findById(userFollowerId));
  }

  @Override
  public String saveUserFollower(UserFollows userFollows) {
    userFollowRepository.save(userFollows);
    return "following saved";
  }

  @Override
  public Optional <List<UserFollows>> getUserFollowsByUserFollowerIdIs(
      Integer userFollower) {
    return userFollowRepository.getUserFollowsByUserFollowerIdIs(userFollower);
  }


}
