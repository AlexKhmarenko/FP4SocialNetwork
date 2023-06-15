package com.danit.socialnetwork.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor(force = true)
@Table(name = "notifications")
public class Notification {
  @Id
  @Column(name = "n_id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Integer notificationId;

  @Column(name = "event_id")
  @NotNull
  Integer eventId;

  @Column(name = "user_id")
  @NotNull
  Integer userId;

  @Column(name = "following_user_id")
  @NotNull
  Integer followingUserId;

  @Column(name = "following_user_photo_link")
  @NotNull
  String userPhoto;

  @Column(name = "notification")
  @NotNull
  String notificationText;

  @CreationTimestamp
  @Column(name = "created_at")
  Timestamp dateTime;

  @Column(name = "notification_read")
  private Boolean notificationRead;


  public Notification(Integer userId, Integer eventId, Integer followingUserId, String userPhoto, String notificationText) {
    this.userId = userId;
    this.eventId = eventId;
    this.followingUserId = followingUserId;
    this.userPhoto = userPhoto;
    this.notificationText = notificationText;
    this.notificationRead = false;
  }
}
