package com.danit.socialnetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@Table(name = "user_follows")
public class UserFollow {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_follow_id")
  private Integer userFollowId;

  @Column(name = "user_following_id")
  private Integer userFollowingId;

  @Column(name = "user_follower_id")
  private Integer userFollowerId;

  @Column(name = "received_notification_post")
  private Boolean receivedNotificationPost;


}
