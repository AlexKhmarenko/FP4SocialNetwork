package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.Notification;
import com.danit.socialnetwork.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  public List<Notification> findAllByFollowerUserId(Integer userId, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return notificationRepository.findAllByFollowerUserId(userId, pageable);
  }

  @Override
  public List<Notification> findAllByFollowingUserId(Integer userId, int page, int pageSize) {
    Pageable pageable = PageRequest.of(page, pageSize);
    return notificationRepository.findAllByFollowingUserId(userId, pageable);
  }

  @Override
  public Notification saveNotification(Notification notification) {
    return notificationRepository.save(notification);
  }

  @Override
  public List<Notification> findAllByFollowerUserIdAndNotificationRead(
      Integer followerUserId, Boolean notificationRead) {
    return notificationRepository.findAllByFollowerUserIdAndNotificationRead(
        followerUserId, notificationRead);
  }

  @Override
  public Notification findNotificationByNotificationId(Integer notificationId) {
    return notificationRepository.findNotificationByNotificationId(notificationId);
  }

}
