package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostDtoResponse;
import com.danit.socialnetwork.dto.post.PostDtoSave;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.model.UserFollower;
import com.danit.socialnetwork.repository.PostRepository;
import com.danit.socialnetwork.repository.UserFollowRepository;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;
  private final UserFollowRepository userFollowRepository;
  private final UserRepository userRepository;

  // Method returns  all posts from users that a user follows by his id
  @Override
  public List<PostDtoResponse> getAllPostsFromToFollow(Integer userFollowerId) {
    Optional<DbUser> tempUser = userRepository.findById(userFollowerId);
    List<UserFollower> userFollowerList = userFollowRepository.findAllByUserFollowerId(tempUser);
    List<PostDtoResponse> dtoPostList = new ArrayList<>();
    for (UserFollower userFollower : userFollowerList) {
      List<Post> postList = new ArrayList<>();
      DbUser dbUser = userFollower.getUserFollowingId();
      postList.addAll(postRepository.findAllByUserPost(dbUser));
      for (Post post : postList) {
        PostDtoResponse dtoPost = PostDtoResponse.from(post);
        dtoPostList.add(dtoPost);
      }
    }
    return dtoPostList;
  }

  // Method returns all available posts
  @Override
  public List<PostDtoResponse> getAllPosts() {
    List<Post> listPost = postRepository.findAll(Sort.by(Sort.Direction.DESC, "sentDateTime"));
    List<PostDtoResponse> postDtoResponseList = listPost.stream()
        .map(PostDtoResponse::from)
        .collect(Collectors.toList());
    return postDtoResponseList;
  }

  // Method save the post and returns it
  @Override
  public Post savePost(PostDtoSave thePostDtoSave) {
    Optional<DbUser> userPost = userRepository.findById(thePostDtoSave.getUserId());
    Post thePostSave = Post.from(thePostDtoSave, userPost.get());
    return postRepository.save(thePostSave);

  }

}
