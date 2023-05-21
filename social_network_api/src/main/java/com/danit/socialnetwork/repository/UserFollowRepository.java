package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserFollowRepository extends JpaRepository<UserFollow, Integer> {


  @Query(nativeQuery = true, value = "select * from user_follows "
      + "where user_follower_id = :userFollowerId "
      + "and received_notification_post = :receivedNotificationPost")
  List<UserFollow> findAllByUserFollowerIdAndReceivedNotificationPostContaining(
      Integer userFollowerId, Boolean receivedNotificationPost);

  @Query(nativeQuery = true, value = "select * from user_follows "
      + "where user_follower_id = :userFollowerId ")
  List<UserFollow> findAllByUserFollowerId(Integer userFollowerId);

  @Query(nativeQuery = true, value = "select * from user_follows "
      + "where user_follower_id = :follower "
      + "and user_following_id = :following")
  Optional<UserFollow> getUserFollowByUserFollowerIdAndUserFollowingId(
      Integer follower, Integer following);
}