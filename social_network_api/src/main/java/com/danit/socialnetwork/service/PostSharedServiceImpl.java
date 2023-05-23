package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostSharedDtoSave;
import com.danit.socialnetwork.model.PostShared;
import com.danit.socialnetwork.repository.PostSharedRepository;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostSharedServiceImpl implements PostSharedService {

  private final PostSharedRepository postSharedRepository;
  private final ModelMapper modelMapper;

  @Override
  public PostShared savePostShared(PostSharedDtoSave thePostSharedDto) {
    PostShared postShared = modelMapper.map(thePostSharedDto, PostShared.class);
    postShared.setPostSharedId(0);
    postShared.setCreatedDateTime(LocalDateTime.now());
    return postSharedRepository.save(postShared);
  }

  @Override
  public List<PostShared> getAllRepostsByUserId(Integer userId) {
    return postSharedRepository.findAllByUserId(userId);
  }


}
