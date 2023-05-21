package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.PasswordChangeRequests;
import com.danit.socialnetwork.repository.PasswordChangeRequestsRepo;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordChangerServiceImpl implements PasswordChangerService {
  private final PasswordChangeRequestsRepo repo;
  private final UserRepository userRepo;

  @Override
  public String saveRequest(String email, String request) {
    PasswordChangeRequests pcr = new PasswordChangeRequests();
    pcr.setEmail(email);
    pcr.setChangeRequest(request);
    repo.save(pcr);
    return "request to change password from " + email;
  }

  @Override
  public boolean changePassword(String email, String password) {
    Optional<DbUser> maybeUser = userRepo.findDbUserByEmail(email);
    if (maybeUser.isPresent()) {
      DbUser refreshUser = maybeUser.get();
      refreshUser.setPassword(password);
      userRepo.save(refreshUser);
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Optional<PasswordChangeRequests> getEmailByUuid(String uuid) {
    return repo.getPasswordChangeRequestsByChangeRequest(uuid);
  }

  @Override
  public void deleteRequestByEmail(String email) {
    repo.deleteById(email);
  }
}