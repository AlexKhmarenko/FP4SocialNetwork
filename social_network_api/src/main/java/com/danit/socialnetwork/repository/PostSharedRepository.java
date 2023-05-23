package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.PostShared;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface PostSharedRepository extends JpaRepository<PostShared, Integer> {
  @Query(nativeQuery = true, value = "SELECT * FROM POST_SHARED WHERE POST_SHARED.USER_ID = :userId "
      + "order by POST_SHARED.CREATED_DATETIME DESC")
  List<PostShared> findAllByUserId(Integer userId);

}
