package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

  @Query(nativeQuery = true, value = "SELECT * FROM POSTS "
      + "LEFT JOIN USER_FOLLOWS ON POSTS.USER_ID = USER_FOLLOWS.USER_FOLLOWING_ID "
      + "where USER_FOLLOWS.USER_FOLLOWER_ID = :userId order by POSTS.SENT_DATETIME DESC")
  List<Post> findAllPostsFromToFollow(Integer userId, Pageable pageable);

}
