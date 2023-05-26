package com.danit.socialnetwork.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.ManyToMany;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "users")
@Data
@NoArgsConstructor(force = true)
public class DbUser {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id")
  private Integer userId;

  @Column(name = "username")
  @NonNull
  private String username;

  @Column(name = "passwords")
  @NonNull
  private String password;

  @Column(name = "email")
  @NonNull
  private String email;

  @Column
  private String address;

  @Column(name = "created_date")
  @NonNull
  @CreationTimestamp
  private LocalDateTime createdDate;

  @Column(name = "name")
  @NonNull
  private String name;

  @Column(name = "dateOfBirth")
  @NonNull
  private LocalDate dateOfBirth;

  @Column(name = "profile_background_image_url", columnDefinition = "text")
  private String profileBackgroundImageUrl;

  @Column(name = "profile_image_url", columnDefinition = "text")
  private String profileImageUrl;

  @ManyToMany(mappedBy = "dbUsers")
  @Fetch(FetchMode.JOIN)
  private Set<Message> messages = new HashSet<>();

  @ManyToMany(mappedBy = "dbUsers")
  @Fetch(FetchMode.JOIN)
  private Set<InboxParticipants> inboxParticipants = new HashSet<>();

  @ManyToMany(mappedBy = "dbUsers")
  @Fetch(FetchMode.JOIN)
  private Set<Inbox> inbox = new HashSet<>();

  public DbUser(String username, String password,
                String email, String name, LocalDate dateOfBirth) {
    this.username = username;
    this.password = password;
    this.email = email;
    this.name = name;
    this.dateOfBirth = dateOfBirth;
  }
}
