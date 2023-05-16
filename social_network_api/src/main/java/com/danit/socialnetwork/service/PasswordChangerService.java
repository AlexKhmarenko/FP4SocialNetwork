package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.PasswordChangeRequests;
import com.danit.socialnetwork.repository.PasswordChangeRequestsRepo;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

public interface PasswordChangerService {
  String saveRequest(String email, String request);

  boolean changePassword(String email, String password);

  Optional<PasswordChangeRequests> getEmailByUuid(String uuid);

  void deleteRequestByEmail(String email);
}