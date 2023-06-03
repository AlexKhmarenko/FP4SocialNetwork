package com.danit.socialnetwork.mappers;

import com.danit.socialnetwork.config.ImageHandlingConf;
import com.danit.socialnetwork.dto.user.EditingDtoRequest;
import com.danit.socialnetwork.model.DbUser;

public class MapperUtils {
  private MapperUtils() {
  }

  public static String getProfileImageUrl(DbUser dbUser) {
    return dbUser.getProfileImageUrl();
  }

  public static String getProfileBackgroundImageUrl(DbUser dbUser) {
    return dbUser.getProfileBackgroundImageUrl();
  }

  public static String encodeProfile(EditingDtoRequest editingDtoRequest) {
    ImageHandlingConf imageHandling = new ImageHandlingConf();
    byte[] profile = editingDtoRequest.getProfileImageUrl();
    if (profile == null) {
      return null;
    }
    return imageHandling.uploadImage(profile);
  }

  public static String encodeProfileBackground(EditingDtoRequest editingDtoRequest) {
    ImageHandlingConf imageHandling = new ImageHandlingConf();
    byte[] profileBackground = editingDtoRequest.getProfileBackgroundImageUrl();
    if (profileBackground == null) {
      return null;
    }
    return imageHandling.uploadImage(profileBackground);
  }

  public static Integer getUserId(DbUser dbUser) {
    return dbUser.getUserId();
  }

  public static String getUsername(DbUser dbUser) {
    return dbUser.getUsername();
  }

  public static String getName(DbUser dbUser) {
    return dbUser.getName();
  }

}
