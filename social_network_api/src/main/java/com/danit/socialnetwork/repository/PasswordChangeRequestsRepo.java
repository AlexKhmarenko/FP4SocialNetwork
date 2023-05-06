package com.danit.socialnetwork.repository;

import com.danit.socialnetwork.model.PasswordChangeRequests;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordChangeRequestsRepo extends JpaRepository<PasswordChangeRequests, String> {
    PasswordChangeRequests getPasswordChangeRequestsByEmail(String email);

    PasswordChangeRequests getPasswordChangeRequestsByChangeRequestContains(String uuid);
}