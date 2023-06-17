package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

  List<Notification> findAllByUserId(Integer userId);
  List<Notification> findAllByFollowingUserId(Integer userId);

//  Optional<Notification> findAllByUserId(Integer userId);
}
