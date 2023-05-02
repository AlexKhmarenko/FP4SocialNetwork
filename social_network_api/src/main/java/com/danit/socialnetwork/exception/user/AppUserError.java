package com.danit.socialnetwork.exception.user;

import com.danit.socialnetwork.exception.AppError;

public class AppUserError extends AppError {

  public AppUserError(String message) {
    super(message);
  }

  public AppUserError(String message, Throwable cause) {
    super(message, cause);
  }

  public AppUserError(Throwable cause) {
    super(cause);
  }
}
