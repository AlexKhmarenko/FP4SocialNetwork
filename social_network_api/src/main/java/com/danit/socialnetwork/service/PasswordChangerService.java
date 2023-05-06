package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.PasswordChangeRequests;
import com.danit.socialnetwork.repository.PasswordChangeRequestsRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PasswordChangerService {
    private final PasswordChangeRequestsRepo repo;

    public String saveRequest(String email, String request) {
        PasswordChangeRequests pcr = new PasswordChangeRequests();
        pcr.setEmail(email);
        pcr.setChangeRequest(request);
        repo.save(pcr);
        return "request to change password from " + email;
    }

    public Optional<PasswordChangeRequests> getEmailByUuid(String uuid) {
        PasswordChangeRequests pcr = repo.getPasswordChangeRequestsByChangeRequestContains(uuid);
        if (pcr != null) {
            return Optional.of(pcr);
        } else {
            return Optional.empty();
        }
    }

    public void deleteRequestByEmail(String email) {
        repo.deleteById(email);
    }
}