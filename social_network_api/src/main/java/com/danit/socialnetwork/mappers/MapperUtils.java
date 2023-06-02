package com.danit.socialnetwork.mappers;

import com.danit.socialnetwork.config.ImageHandlingConf;
import com.danit.socialnetwork.dto.user.EditingDtoRequest;
import com.danit.socialnetwork.model.DbUser;

public class MapperUtils {
  private MapperUtils() {
  }

  public static byte[] decodeProfile(DbUser dbUser) {
    ImageHandlingConf imageHandling = new ImageHandlingConf();
    String profile = dbUser.getProfileImageUrl();
    if (profile == null) {
      return null;
    }
    return imageHandling.getImage(profile);
  }

  public static byte[] decodeProfileBackground(DbUser dbUser) {
    ImageHandlingConf imageHandling = new ImageHandlingConf();
    String profileBackground = dbUser.getProfileBackgroundImageUrl();
    if (profileBackground == null) {
      return null;
    }
    return imageHandling.getImage(profileBackground);
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
