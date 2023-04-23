package com.danit.socialnetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class DbUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "username")
  private String username;

  @Column(name = "password")
  private String password;

  @Column(name = "email")
  private String email;

  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @Column(name = "name")
  private String name;

  @Column(name = "profile_background_image_url")
  private String profileBackgroundImageUrl;

  @Column(name = "profile_image_url")
  private String profileImageUrl;

  public DbUser(String username, String password, String email, String name) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
  }
}
