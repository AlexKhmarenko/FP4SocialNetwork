package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.UserFollows;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollows, Integer> {

  List<UserFollows> findAllByUserFollowerIdAndReceivedNotificationPostContaining(
      Integer userFollowerId, Boolean receivedNotificationPost
  );

  List<UserFollows> findAllByUserFollowerId(Integer userFollowerId);

  Optional <List<UserFollows>> getUserFollowerByUserFollowerId(
      Integer userFollower);

  Optional<List<UserFollows>> getUserFollowsByUserFollowerIdIs(Integer follower);


}
