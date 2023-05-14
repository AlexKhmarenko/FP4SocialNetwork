package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Integer> {

  List<Post> findAllByUserPost(DbUser user);

}
