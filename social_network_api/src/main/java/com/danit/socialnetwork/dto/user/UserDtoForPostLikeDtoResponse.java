package com.danit.socialnetwork.dto.user;

import com.danit.socialnetwork.model.DbUser;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDtoForPostLikeDtoResponse {

  private Integer userId;

  private String username;

  private String name;

  public static UserDtoForPostLikeDtoResponse from(DbUser dbUser) {
    UserDtoForPostLikeDtoResponse userDtoResponse = new UserDtoForPostLikeDtoResponse();
    userDtoResponse.setName(dbUser.getName());
    userDtoResponse.setUsername(dbUser.getUsername());
    userDtoResponse.setUserId(dbUser.getUserId());
    return userDtoResponse;
  }

}
