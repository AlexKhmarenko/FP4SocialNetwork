package com.danit.socialnetwork.model;


import com.danit.socialnetwork.dto.post.PostDtoSave;
import com.danit.socialnetwork.dto.post.PostLikeDto;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Base64;

@Entity
@Table(name = "post_likes")
@Data
@NoArgsConstructor
public class PostLike {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "post_like_id")
  private Integer postLikeId;

  @Column(name = "created_datetime", updatable = false)
  @NonNull
  @CreationTimestamp
  private LocalDateTime createdDateTime;

  @ManyToOne(targetEntity = DbUser.class)
  @JoinColumn(name = "user_id")
  private DbUser userPostLike;

  @ManyToOne(targetEntity = Post.class)
  @JoinColumn(name = "post_id")
  private Post postInPostLike;


}
