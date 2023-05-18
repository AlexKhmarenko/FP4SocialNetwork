package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.post.PostDtoResponse;
import com.danit.socialnetwork.dto.post.PostDtoSave;
import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class PostRestController {
  private final PostService postService;

  @GetMapping(path = "/posts", produces = "application/json")
  @ResponseBody
  public List<PostDtoResponse> getAllPosts(@RequestParam(name = "userId",
      defaultValue = "0") Integer useFollowingId, @RequestParam(name = "page", defaultValue = "0") Integer page) {
    if (useFollowingId == 0) {
      return postService.getAllPosts(page);
    }
    return postService.getAllPostsFromToFollowWithNativeQuery(useFollowingId, page);
  }

  @PostMapping(path = "/posts", consumes = "application/json", produces = "application/json")
  public ResponseEntity<PostDtoResponse> addPost(@RequestBody PostDtoSave thePostDtoSave) {
    Post dbPost = postService.savePost(thePostDtoSave);
    PostDtoResponse postDtoResponse = PostDtoResponse.from(dbPost);
    System.out.println(Arrays.toString(postDtoResponse.getPhotoFileByteArray()));
    ResponseEntity<PostDtoResponse> responseEntity = new ResponseEntity<>(PostDtoResponse.from(dbPost), HttpStatus.CREATED);
    System.out.println(Arrays.toString(responseEntity.getBody().getPhotoFileByteArray()));
    return new ResponseEntity<>(PostDtoResponse.from(dbPost), HttpStatus.CREATED);
  }

}
