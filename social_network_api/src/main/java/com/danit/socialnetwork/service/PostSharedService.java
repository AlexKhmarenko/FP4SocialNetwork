package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostSharedDtoSave;

import com.danit.socialnetwork.model.PostShared;

import java.util.List;

public interface PostSharedService {
  PostShared savePostShared(PostSharedDtoSave thePostSharedDto);

  List<PostShared> getAllRepostsByUserId(Integer userId);
}
