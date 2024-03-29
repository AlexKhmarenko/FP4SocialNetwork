package com.danit.socialnetwork.dto.search;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SearchDto {
  private Integer userId;
  private String username;
  private String name;
  private String profileImageUrl;

  public SearchDto(Integer userId, String username, String name, String profileImageUrl) {
    this.userId = userId;
    this.username = username;
    this.name = name;
    this.profileImageUrl = profileImageUrl;
  }
}
