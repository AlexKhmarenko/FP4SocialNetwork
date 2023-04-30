package com.danit.socialnetwork.service;

import com.danit.socialnetwork.exception.user.HeaderPhotoNotFoundException;
import com.danit.socialnetwork.exception.user.PhotoNotFoundException;
import com.danit.socialnetwork.exception.user.UserNotFoundException;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  private <T extends Object> Boolean isEmpty(T t) {
    Optional<T> maybeExist = Optional.ofNullable(t);
    if (maybeExist.isEmpty()) return true;
    else return false;

  }

  @Override
  public DbUser findByUsername(String username) {
    DbUser maybeUser = userRepository.findByUsername(username);
    if (isEmpty(maybeUser)) throw new UserNotFoundException("User with username " + username + " not found");
    else return maybeUser;
  }

  @Override
  public byte[] getProfileImage(String username) throws IOException {
    String profileImagePath = userRepository.findByUsername(username).getProfileImageUrl();
    if (isEmpty(profileImagePath))
      throw new PhotoNotFoundException("Photo for user with username " + username + " is absent");
    else {
      InputStream in = getClass().getResourceAsStream(profileImagePath);
      if (isEmpty(in)) throw new PhotoNotFoundException("Wrong path to photo for user with username " + username);
      else return FileCopyUtils.copyToByteArray(in);
    }
  }

  @Override
  public byte[] getBackgroundImage(String username) throws IOException {
    String profileBackgroundImagePath = userRepository.findByUsername(username).getProfileBackgroundImageUrl();
    if (isEmpty(profileBackgroundImagePath))
      throw new HeaderPhotoNotFoundException("Header photo for user with username " + username + " is absent");
    else {
      InputStream in = getClass().getResourceAsStream(profileBackgroundImagePath);
      if (isEmpty(in))
        throw new HeaderPhotoNotFoundException("Wrong path to header photo for user with username " + username);
      else return FileCopyUtils.copyToByteArray(in);
    }
  }

}
