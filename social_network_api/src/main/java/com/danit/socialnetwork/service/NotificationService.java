package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {

  List<Notification> findAllByUserId(Integer userId);

  void saveNotification(Notification notification);

  List<Notification> findAllByFollowingUserId(Integer userId);
}
