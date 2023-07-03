package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

  List<Notification> findAllByFollowerUserId(Integer userId, Pageable pageable);

  List<Notification> findAllByFollowingUserId(Integer userId, Pageable pageable);

  List<Notification> findAllByFollowerUserIdAndNotificationRead(Integer followerUserId, Boolean notificationRead);

  Notification findNotificationByNotificationId(Integer notificationId);
}
