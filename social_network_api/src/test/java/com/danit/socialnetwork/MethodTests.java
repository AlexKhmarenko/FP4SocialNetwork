package com.danit.socialnetwork;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.PasswordChangeRequests;
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

  @Autowired
  private PasswordChangerServiceImpl passwordChangerService;

  @Mock
  private UserRepository userRepository;


  @Test
  public void findByUsername() {
    DbUser dbUser = new DbUser();
    dbUser.setUsername("Alex");
    when (userRepository.findByUsername("Alex")).thenReturn(Optional.of(dbUser));

    Optional<DbUser> user = userRepository.findByUsername("Alex");
    String username = user.get().getUsername();
    assertEquals("Alex", username);
  }

  @Test
  public void findDbUserByEmail() {
    DbUser dbUser = new DbUser();
    dbUser.setUsername("Alex");
    dbUser.setEmail("khmarenko.a@gmail.com");
    when (userRepository.findDbUserByEmail("khmarenko.a@gmail.com")).thenReturn(Optional.of(dbUser));
    Optional<DbUser> user = userRepository.findDbUserByEmail("khmarenko.a@gmail.com");
    String username = user.get().getUsername();
    assertEquals("Alex", username);
  }

  @Test
  public void saveRequest() {
    PasswordChangeRequests passwordChangeRequests = new PasswordChangeRequests();
    String fine = "request to change password from test@test.com";
    when(passwordChangerService.saveRequest("test@test.com", "123456")).thenReturn(fine);


    String text = passwordChangerService.saveRequest("test@test.com", "123456");
    assertEquals("request to change password from test@test.com", text);
  }
}
