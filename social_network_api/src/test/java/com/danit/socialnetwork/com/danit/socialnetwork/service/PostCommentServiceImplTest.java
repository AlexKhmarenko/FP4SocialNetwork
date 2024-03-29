package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostCommentDtoSave;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.model.PostComment;
import com.danit.socialnetwork.repository.PostCommentRepository;
import com.danit.socialnetwork.repository.PostRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostCommentServiceImplTest {
  @InjectMocks
  PostCommentServiceImpl postCommentService;
  @Mock
  PostCommentRepository postCommentRepository;
  @Mock
  PostRepository postRepository;
  @Mock
  ModelMapper modelMapper;

  @Test
  void savePostComment() {

    Integer postId = 2;
    Integer userId1 = 3;
    Integer userId2 = 2;

    DbUser dbUser1 = new DbUser();
    dbUser1.setUserId(userId1);

    DbUser dbUser2 = new DbUser();
    dbUser2.setUserId(userId2);
    dbUser2.setUsername("Nick");

    Post post = new Post();
    post.setPostId(postId);
    post.setUserPost(dbUser1);
    post.setPostComments(new ArrayList<PostComment>());

    PostComment postComment1 = new PostComment();
    postComment1.setPostId(post);
    postComment1.setCommentText("Hello world 1");
    postComment1.setUserId(dbUser2);
    postComment1.setPostCommentId(1);

    PostCommentDtoSave postCommentDtoSave = new PostCommentDtoSave();
    postCommentDtoSave.setCommentText("Hey Hello world!");
    postCommentDtoSave.setUserId(userId1);
    postCommentDtoSave.setPostId(postId);

    when(postRepository.findById(postCommentDtoSave.getPostId())).thenReturn(Optional.of(post));
    when(modelMapper.map(postCommentDtoSave, PostComment.class)).thenReturn(postComment1);
    when(postRepository.save(any(Post.class))).thenReturn(post);
    PostComment response = postCommentService.savePostComment(postCommentDtoSave);

    Assertions.assertEquals(userId2, response.getUserId().getUserId());
    Assertions.assertEquals(postId, response.getPostId().getPostId());
    Assertions.assertEquals("Hello world 1", response.getCommentText());

  }

  @Test
  void getAllPostCommentsByPostId() {
    Integer postId = 2;
    Integer page = 0;

    Integer userId1 = 1;
    Integer userId2 = 2;
    Integer userId3 = 3;

    DbUser dbUser1 = new DbUser();
    dbUser1.setUserId(userId1);
    dbUser1.setUsername("Tom");

    DbUser dbUser2 = new DbUser();
    dbUser2.setUserId(userId2);
    dbUser2.setUsername("Nick");

    DbUser dbUser3 = new DbUser();
    dbUser3.setUserId(userId3);
    dbUser3.setUsername("John");

    Post post = new Post();
    post.setPostId(postId);
    post.setUserPost(dbUser1);
    post.setPostComments(new ArrayList<>());

    Pageable pagedByTenPosts =
        PageRequest.of(page, 10);

    PostComment postComment1 = new PostComment();
    postComment1.setPostId(post);
    postComment1.setCommentText("Hello world 1");
    postComment1.setUserId(dbUser2);
    postComment1.setPostCommentId(1);

    PostComment postComment2 = new PostComment();
    postComment2.setPostId(post);
    postComment2.setCommentText("Hello world 2");
    postComment2.setUserId(dbUser3);
    postComment2.setPostCommentId(2);


    List<PostComment> tempPostCommentList = Arrays.asList(postComment1, postComment2);
    when(postCommentRepository.findAllCommentsByPostId(postId, pagedByTenPosts)).thenReturn(tempPostCommentList);
    List<PostComment> postCommentList = postCommentService.getAllPostCommentsByPostId(postId, page);
    Assertions.assertEquals(2, postCommentList.size());
    Assertions.assertEquals(postId, postCommentList.get(0).getPostId().getPostId());
    Assertions.assertEquals(userId2, postCommentList.get(0).getUserId().getUserId());
    Assertions.assertEquals(userId3, postCommentList.get(1).getUserId().getUserId());
    Assertions.assertEquals("Nick", postCommentList.get(0).getUserId().getUsername());

  }

  @Test
  void deletePostComment() {

    Integer postId = 2;
    Integer userId = 2;

    DbUser dbUser = new DbUser();
    dbUser.setUserId(userId);
    dbUser.setUsername("Nick");

    Post post = new Post();
    post.setPostId(postId);

    PostComment postComment = new PostComment();
    postComment.setPostId(post);
    postComment.setCommentText("Hello world 1");
    postComment.setUserId(dbUser);
    postComment.setPostCommentId(1);

    when(postCommentRepository.findById(postComment.getPostCommentId())).thenReturn(Optional.of(postComment));
    PostComment response = postCommentService.deletePostComment(postComment.getPostCommentId());

    Assertions.assertEquals(userId, response.getUserId().getUserId());
    Assertions.assertEquals(postId, response.getPostId().getPostId());
    Assertions.assertEquals("Hello world 1", response.getCommentText());


  }
}