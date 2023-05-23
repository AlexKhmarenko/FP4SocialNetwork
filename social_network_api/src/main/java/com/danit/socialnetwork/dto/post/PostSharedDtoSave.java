package com.danit.socialnetwork.dto.post;

import com.danit.socialnetwork.model.PostShared;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostSharedDtoSave {

  Integer postId;
  Integer userId;

  public static PostSharedDtoSave from(PostShared postShared) {
    PostSharedDtoSave postSharedDto = new PostSharedDtoSave();
    postSharedDto.setPostId(postShared.getPostId().getPostId());
    postSharedDto.setUserId(postShared.getUserId().getUserId());
    return postSharedDto;
  }

}
