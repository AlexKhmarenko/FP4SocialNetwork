package com.danit.socialnetwork.exception.post;

import com.danit.socialnetwork.exception.AppError;

public class AppPostError extends AppError {
  public AppPostError(String message) {
    super(message);
  }

  public AppPostError(String message, Throwable cause) {
    super(message, cause);
  }

  public AppPostError(Throwable cause) {
    super(cause);
  }
}
