package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostDtoResponse;
import com.danit.socialnetwork.dto.post.PostDtoSave;
import com.danit.socialnetwork.model.Post;
import org.springframework.http.HttpStatus;

import java.util.List;

public interface PostService {

  List<PostDtoResponse> getAllPosts(Integer page);

  List<PostDtoResponse> getAllPostsFromToFollowWithNativeQuery(Integer userFollowerId, Integer page);

  Post savePost(PostDtoSave thePostDtoSave);

  List<PostDtoResponse> getAllOwnPosts(Integer userId, Integer page);

  List<PostDtoResponse> getAllLikedPosts(Integer userId, Integer page);

  List<PostDtoResponse> getAllPostsAndRepostsByUserId(Integer userId, Integer page);

  List<PostDtoResponse> getAllPostsWithShowingRepostByUserId(Integer userId, Integer page);

  PostDtoResponse getPostByPostId(Integer postId, Integer userId);

  Integer findLatestPostIdByUserId(Integer userId);

  HttpStatus addViews(Integer[] postIdArray);

  Post findPostByPostId(Integer postId);

}
