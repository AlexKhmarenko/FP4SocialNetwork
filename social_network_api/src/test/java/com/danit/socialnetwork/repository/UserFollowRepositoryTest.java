package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.UserFollow;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserFollowRepositoryTest {

  @Mock
  private UserFollowRepository userFollowRepository;

  @Test
  void getUserFollowByUserFollowerIdAndUserFollowingId() {
    UserFollow userFollow = new UserFollow();
    userFollow.setUserFollowId(1);
    userFollow.setUserFollowerId(5);
    userFollow.setUserFollowingId(3);
    when(userFollowRepository.getUserFollowByUserFollowerIdAndUserFollowingId(5, 3)).thenReturn(Optional.of(userFollow));
    Optional<UserFollow> maybeUserFollow = userFollowRepository.getUserFollowByUserFollowerIdAndUserFollowingId(5, 3);
    Integer id = maybeUserFollow.get().getUserFollowId();
    assertEquals(Optional.of(1).get(), id);
  }
}