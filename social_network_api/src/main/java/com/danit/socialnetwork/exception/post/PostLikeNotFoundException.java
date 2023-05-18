package com.danit.socialnetwork.exception.post;

public class PostLikeNotFoundException extends AppPostError {
  public PostLikeNotFoundException(String message) {
    super(message);
  }

  public PostLikeNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public PostLikeNotFoundException(Throwable cause) {
    super(cause);
  }
}
