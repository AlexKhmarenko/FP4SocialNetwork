package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostCommentDtoSave;
import com.danit.socialnetwork.exception.post.PostCommentNotFoundException;
import com.danit.socialnetwork.exception.post.PostNotFoundException;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.model.PostComment;
import com.danit.socialnetwork.repository.PostCommentRepository;
import com.danit.socialnetwork.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostCommentServiceImpl implements PostCommentService {

  private final PostCommentRepository postCommentRepository;
  private final PostRepository postRepository;
  private final ModelMapper modelMapper;

  @Override
  public PostComment savePostComment(PostCommentDtoSave postCommentDtoSave) {
    Integer postId = postCommentDtoSave.getPostId();
    Optional<Post> optionalPost = postRepository.findById(postId);
    if (optionalPost.isEmpty()) {
      throw new PostNotFoundException(String.format("Post with postId %s not found",
          postId));
    }
    Post tempPost = optionalPost.get();
    PostComment postComment = modelMapper.map(postCommentDtoSave, PostComment.class);
    ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
    LocalDateTime utcDateTime = currentDateTime.toLocalDateTime();
    postComment.setCreatedDateTime(utcDateTime);
    postComment.setPostCommentId(0);
    Integer countCommentsBefore = tempPost.getPostComments().size();
    tempPost.getPostComments().add(postComment);
    tempPost = postRepository.save(tempPost);
    Integer countCommentsAfter = tempPost.getPostComments().size();
    if ((countCommentsAfter - countCommentsBefore) == 1) {
      return tempPost.getPostComments().get(tempPost.getPostComments().size() - 1);
    } else {
      return null;
    }
  }


  @Override
  public List<PostComment> getAllPostCommentsByPostId(Integer postId, Integer page) {
    Pageable pagedByTenPosts =
        PageRequest.of(page, 10);
    return postCommentRepository.findAllCommentsByPostId(postId, pagedByTenPosts);
  }

  @Override
  public PostComment deletePostComment(Integer postCommentId) {
    Optional<PostComment> optionalPostComment = postCommentRepository.findById(postCommentId);
    if (optionalPostComment.isEmpty()) {
      throw new PostCommentNotFoundException(String.format("Comment with postCommentId %s not found", postCommentId));
    }
    postCommentRepository.deleteById(postCommentId);
    return optionalPostComment.get();
  }
}
