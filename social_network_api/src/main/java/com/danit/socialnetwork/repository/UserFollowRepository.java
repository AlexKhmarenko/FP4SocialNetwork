package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {

  List<UserFollow> findAllByUserFollowerIdAndReceivedNotificationPostContaining(
      Integer userFollowerId, Boolean receivedNotificationPost
  );
  List<UserFollow> findAllByUserFollowerId(Integer userFollowerId);
  Optional<UserFollow> getUserFollowByUserFollowerIdAndUserFollowingId(Integer follower, Integer following);
}
