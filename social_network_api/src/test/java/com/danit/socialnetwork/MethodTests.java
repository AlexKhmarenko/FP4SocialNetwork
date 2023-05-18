package com.danit.socialnetwork;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.PasswordChangeRequests;
import com.danit.socialnetwork.repository.PasswordChangeRequestsRepo;
import com.danit.socialnetwork.repository.UserRepository;
import com.danit.socialnetwork.service.PasswordChangerServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MethodTests {

  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordChangeRequestsRepo passwordChangeRequestsRepo;


  @Test
  public void findByUsername() {
    DbUser dbUser = new DbUser();
    dbUser.setUsername("Alex");
    when(userRepository.findByUsername("Alex")).thenReturn(Optional.of(dbUser));

    Optional<DbUser> user = userRepository.findByUsername("Alex");
    String username = user.get().getUsername();
    assertEquals("Alex", username);
  }

  @Test
  public void findDbUserByEmail() {
    DbUser dbUser = new DbUser();
    dbUser.setUsername("Alex");
    dbUser.setEmail("khmarenko.a@gmail.com");
    when(userRepository.findDbUserByEmail("khmarenko.a@gmail.com")).thenReturn(Optional.of(dbUser));
    Optional<DbUser> user = userRepository.findDbUserByEmail("khmarenko.a@gmail.com");
    String username = user.get().getUsername();
    assertEquals("Alex", username);
  }

  @Test
  public void saveRequest() {
    PasswordChangeRequests passwordChangeRequests = new PasswordChangeRequests();
    passwordChangeRequests.setEmail("test@test.com");
    passwordChangeRequests.setChangeRequest("123456");
    when(passwordChangeRequestsRepo.getPasswordChangeRequestsByChangeRequest("123456")).thenReturn(Optional.of(passwordChangeRequests));
    Optional<PasswordChangeRequests> maybeRequest = passwordChangeRequestsRepo.getPasswordChangeRequestsByChangeRequest("123456");
    String email = maybeRequest.get().getEmail();
    assertEquals("test@test.com", email);
  }
}
