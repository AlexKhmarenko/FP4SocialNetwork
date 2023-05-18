package com.danit.socialnetwork.repository;


import com.danit.socialnetwork.model.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike, Integer> {

  @Query(nativeQuery = true, value = "SELECT * FROM POST_LIKES WHERE POST_LIKES.POST_ID = :postId")
  List<PostLike> findAllByPostId(Integer postId);

  @Query(nativeQuery = true, value = "SELECT * FROM POST_LIKES" +
      " WHERE POST_LIKES.POST_ID = :postId AND POST_LIKES.USER_ID = :userId")
  Optional<PostLike> findPostLikeByPostIdAndUserId(Integer postId, Integer userId);

}
