package com.danit.socialnetwork.service;


import com.danit.socialnetwork.dto.post.PostDtoResponse;
import com.danit.socialnetwork.dto.post.PostDtoSave;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.model.PostComment;
import com.danit.socialnetwork.model.PostLike;
import com.danit.socialnetwork.model.UserFollows;
import com.danit.socialnetwork.repository.PostRepository;
import com.danit.socialnetwork.repository.UserFollowRepository;
import com.danit.socialnetwork.repository.UserRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class PostServiceImplTest extends TestCase {

  @Mock
  PostRepository postRepository;
  @Mock
  UserRepository userRepository;
  @Mock
  UserFollowRepository userFollowRepository;
  @Mock
  PostService postService;

  @Before
  public void setUp() {
    postService = new PostServiceImpl(postRepository, userFollowRepository, userRepository);
  }

  @Test
  public void testGetAllPostsFromToFollow() {

    DbUser user = new DbUser();
    user.setUserId(1);
    user.setUsername("John1");
    user.setName("Johny1");

    DbUser user1 = new DbUser();
    user1.setUserId(2);
    user1.setUsername("Tom");
    user1.setName("Tommy");

    DbUser user2 = new DbUser();
    user2.setUserId(3);
    user2.setUsername("Jim");
    user2.setName("Jimmy");

    UserFollows userFollows1 = new UserFollows();
    userFollows1.setUserFollowId(1);
    userFollows1.setUserFollowerId(user);
    userFollows1.setUserFollowingId(user1);

    UserFollows userFollows2 = new UserFollows();
    userFollows2.setUserFollowId(2);
    userFollows2.setUserFollowerId(user);
    userFollows2.setUserFollowingId(user2);

    Post post1 = new Post();
    post1.setPostId(1);
    post1.setUserPost(user1);
    post1.setWrittenText("Hello world1");
    post1.setPhotoFile("MTA6MjQ6MjY=");
    LocalDateTime dateTime = LocalDateTime.now();
    post1.setSentDateTime(dateTime);
    post1.setPostLikes(new ArrayList<PostLike>() {
    });
    post1.setPostComments(new ArrayList<PostComment>() {
    });

    Post post2 = new Post();
    post2.setPostId(2);
    post2.setUserPost(user2);
    post2.setWrittenText("Hello world2");
    post2.setPhotoFile("MTA6MjQ6MjY=");
    LocalDateTime dateTime2 = LocalDateTime.now();
    post2.setSentDateTime(dateTime2);
    post2.setPostLikes(new ArrayList<PostLike>() {
    });
    post2.setPostComments(new ArrayList<PostComment>() {
    });

    List<Post> postList = new ArrayList<>(Arrays.asList(post1, post2));
    Pageable pagedByFivePosts =
        PageRequest.of(0, 12);

    List<UserFollows> userFollowsList = new ArrayList<>(Arrays.asList(userFollows1, userFollows2));

    when(postRepository.findAllPostsFromToFollow(user.getUserId(), pagedByFivePosts)).thenReturn(postList);

    List<PostDtoResponse> result = postService.getAllPostsFromToFollowWithNativeQuery(
        user.getUserId(), 0);
    Assertions.assertEquals(result.get(0).getWrittenText(), post1.getWrittenText());
    Assertions.assertEquals(result.get(1).getWrittenText(), post2.getWrittenText());
    Assertions.assertEquals(result.get(0).getUsername(), user1.getUsername());
    Assertions.assertEquals(result.get(1).getUsername(), user2.getUsername());
    Assertions.assertEquals(2, result.toArray().length);

  }

  @Test
  public void testGetAllPosts() {
    Post post1 = new Post();
    post1.setPostId(1);
    DbUser user = new DbUser();
    user.setUsername("John1");
    user.setName("Johny1");
    post1.setUserPost(user);
    post1.setWrittenText("Hello world1");
    post1.setPhotoFile("MTA6MjQ6MjY=");
    LocalDateTime dateTime = LocalDateTime.now();
    post1.setSentDateTime(dateTime);
    post1.setPostLikes(new ArrayList<PostLike>() {
    });
    post1.setPostComments(new ArrayList<PostComment>() {
    });

    Post post2 = new Post();
    post2.setPostId(2);
    DbUser user2 = new DbUser();
    user2.setUsername("John2");
    user2.setName("Johny2");
    post2.setUserPost(user);
    post2.setWrittenText("Hello world2");
    post2.setPhotoFile("MTA6MjQ6MjY=");
    LocalDateTime dateTime2 = LocalDateTime.now();
    post2.setSentDateTime(dateTime2);
    post2.setPostLikes(new ArrayList<PostLike>() {
    });
    post2.setPostComments(new ArrayList<PostComment>() {
    });

    Pageable sortedByDateTimeDesc =
        PageRequest.of(0, 12, Sort.by("sentDateTime").descending());

    List<Post> postList = new ArrayList<>(Arrays.asList(post1, post2));
    Page<Post> pagePost = new PageImpl<>(postList);

    System.out.println(postList);
    when(postRepository.findAll(sortedByDateTimeDesc)).thenReturn(pagePost);
    List<PostDtoResponse> result = postService.getAllPosts(0);
    System.out.println(result);
    Assertions.assertEquals(result.get(0).getWrittenText(), post1.getWrittenText());
    Assertions.assertEquals(result.get(1).getName(), post2.getUserPost().getName());
    Assertions.assertEquals(2, result.toArray().length);

  }

  @Test
  public void testSavePost() {

    PostDtoSave postDtoSave = new PostDtoSave();
    postDtoSave.setUserId(2);
    postDtoSave.setWrittenText("Hello world1");
    postDtoSave.setPhotoFileByteArray(new byte[]{49, 48, 58, 50, 52, 58, 50, 54});

    DbUser user = new DbUser();
    user.setUserId(2);
    user.setUsername("John1");
    user.setName("Johny1");

    Post tempPost = Post.from(postDtoSave, user);

    when(userRepository.findById(postDtoSave.getUserId())).thenReturn(Optional.of(user));

    when(postRepository.save(any(Post.class))).thenReturn(tempPost);


    Post post = postService.savePost(postDtoSave);
    System.out.println(post);

    Assertions.assertEquals(post.getWrittenText(), postDtoSave.getWrittenText());
    Assertions.assertEquals(post.getUserPost().getUsername(), user.getUsername());


  }
}