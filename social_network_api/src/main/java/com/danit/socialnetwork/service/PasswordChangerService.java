package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.PasswordChangeRequests;

import java.util.Optional;

public interface PasswordChangerService {

  String saveRequest(String email, String request);

  boolean changedPassword(String email, String password);

  Optional<PasswordChangeRequests> getEmailByUuid(String uuid);

  void deleteRequestByEmail(String email);
}