package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostDtoResponse;
import com.danit.socialnetwork.dto.post.PostDtoSave;
import com.danit.socialnetwork.model.Post;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface PostService {

  List<PostDtoResponse> getAllPosts(Integer page);

  List<PostDtoResponse> getAllPostsFromToFollowWithNativeQuery(Integer userFollowerId, Integer page);

  Post savePost(PostDtoSave thePostDtoSave);

  List<PostDtoResponse> getAllOwnPosts(Integer userId, Integer page);
}
