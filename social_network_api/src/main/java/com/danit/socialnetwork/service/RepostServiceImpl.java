package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.post.PostDtoResponse;
import com.danit.socialnetwork.dto.post.RepostDtoResponse;
import com.danit.socialnetwork.dto.post.RepostDtoSave;
import com.danit.socialnetwork.exception.post.RepostNotFoundException;
import com.danit.socialnetwork.model.Repost;
import com.danit.socialnetwork.repository.PostLikeRepository;
import com.danit.socialnetwork.repository.RepostRepository;
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
public class RepostServiceImpl implements RepostService {

  private final RepostRepository repostRepository;
  private final ModelMapper modelMapper;
  private final PostLikeRepository postLikeRepository;

  private RepostDtoResponse from(Repost repost, String userTimeZone) {
    RepostDtoResponse repostDtoResponse = RepostDtoResponse.from(repost, userTimeZone);
    repostDtoResponse.setLikesCount(postLikeRepository
        .findCountAllLikesByPostId(repost.getPostId().getPostId()));
    repostDtoResponse.setPostCommentsCount(repost.getPostId().getPostComments().size());
    repostDtoResponse.setIsReposted((repostRepository.findRepostByPostIdAndUserId(
        repost.getPostId().getPostId(), repost.getUserId().getUserId())).isPresent());
    repostDtoResponse.setRepostsCount(repostRepository.findCountAllRepostsByPostId(
        repost.getPostId().getPostId()));

    return repostDtoResponse;
  }


  /*Method save repost*/
  @Override
  public Repost saveRepost(RepostDtoSave theRepostDto) {
    Optional<Repost> tempRepost = repostRepository.findRepostByPostIdAndUserId(
        theRepostDto.getPostId(), theRepostDto.getUserId());
    if (tempRepost.isPresent()) {
      return tempRepost.get();
    }
    Repost repost = this.modelMapper.map(theRepostDto, Repost.class);
    repost.setSharedId(0);
    ZonedDateTime currentDateTime = ZonedDateTime.now(ZoneOffset.UTC);
    LocalDateTime utcDateTime = currentDateTime.toLocalDateTime();
    repost.setRepostedDateTime(utcDateTime);
    return repostRepository.save(repost);
  }

  /*Method returns al the posts liked by user and paginated by 10 in descending order*/
  @Override
  public List<RepostDtoResponse> getAllRepostsByUserId(Integer userId, Integer page, String userTimeZone) {
    Pageable pagedByTenPosts =
        PageRequest.of(page, 10);
    List<Repost> repostList = repostRepository.findAllByUserId(userId, pagedByTenPosts);
    return repostList.stream()
        .map(repost -> this.from(repost, userTimeZone))
        .toList();
  }

  @Override
  public Repost deleteRepost(Integer postId, Integer userId) {
    Optional<Repost> tempRepost = repostRepository.findRepostByPostIdAndUserId(
        postId, userId);
    if (tempRepost.isEmpty()) {
      throw new RepostNotFoundException(String.format("Repost for postId %s by userId %s not found ",
          postId, userId));
    }
    repostRepository.delete(tempRepost.get());
    return tempRepost.get();

  }


  @Override
  public Boolean isActiveRepost(Integer postId, Integer userId) {
    Optional<Repost> tempRepost = repostRepository.findRepostByPostIdAndUserId(postId, userId);
    return tempRepost.isPresent();
  }

}
