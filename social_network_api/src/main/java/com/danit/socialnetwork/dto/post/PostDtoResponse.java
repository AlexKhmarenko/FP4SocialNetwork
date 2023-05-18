package com.danit.socialnetwork.dto.post;

import com.danit.socialnetwork.model.Post;
import com.danit.socialnetwork.model.PostComment;
import com.danit.socialnetwork.model.PostLike;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
public class PostDtoResponse {

  private Integer postId;
  private String username;
  private String name;
  private String writtenText;
  private byte[] photoFileByteArray;
  private LocalDateTime sentDateTime;
  //  private List<PostLike> postLikes;
  private List<PostComment> postComments;

  public PostDtoResponse(Integer postId, String username, String name, String writtenText, byte[] photoFileByteArray) {
    this.postId = postId;
    this.username = username;
    this.name = name;
    this.writtenText = writtenText;
    this.photoFileByteArray = photoFileByteArray;
  }

  public static PostDtoResponse from(Post post) {

    PostDtoResponse tempPostDto = new PostDtoResponse();
    tempPostDto.setPostId(post.getPostId());
    tempPostDto.setUsername(post.getUserPost().getUsername());
    tempPostDto.setName(post.getUserPost().getName());
    tempPostDto.setWrittenText(post.getWrittenText());
    tempPostDto.setPhotoFileByteArray(Base64.getDecoder().decode(post.getPhotoFile()));
    tempPostDto.setSentDateTime(post.getSentDateTime());
//    if (Objects.nonNull(post.getPostLikes())) {
//      tempPostDto.setPostLikes(post.getPostLikes());
//    }
    if (Objects.nonNull(post.getPostComments())) {
      tempPostDto.setPostComments(post.getPostComments());
    }
    return tempPostDto;

  }


}
