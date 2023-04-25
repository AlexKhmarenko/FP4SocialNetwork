package com.danit.socialnetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class DbUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;

  @NonNull
  @Column(name = "username")
  private String username;

  @NonNull
  @Column(name = "password")
  private String password;

  @NonNull
  @Column(name = "email")
  private String email;

  //  @NonNull
  @CreationTimestamp
  @Column(name = "created_date")
  private LocalDateTime createdDate;

  @NonNull
  @Column(name = "name")
  private String name;

  @Column(name = "profile_background_image_url")
  private String profileBackgroundImageUrl;

  @Column(name = "profile_image_url")
  private String profileImageUrl;

  @Column(name = "activation_code")
  private String activationCode;

  private String roles;

  private final static String DELIMITER = ":";

  public DbUser(String activationCode, String username, String password,
                String email, String name, String... roles) {
    this.activationCode = activationCode;
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    setRoles(roles);
  }

  public String[] getRoles() {
    return Optional.ofNullable(roles)
        .map(x -> x.split(DELIMITER))
        .orElseGet(() -> new String[]{});
  }

  public void setRoles(String[] roles) {
    this.roles = String.join(DELIMITER, roles);
  }
}
