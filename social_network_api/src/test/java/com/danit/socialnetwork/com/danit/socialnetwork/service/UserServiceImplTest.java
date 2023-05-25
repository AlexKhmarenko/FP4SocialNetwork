package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.search.SearchDto;
import com.danit.socialnetwork.dto.user.UserDtoResponse;
import com.danit.socialnetwork.mappers.SearchMapper;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.UserFollowRepository;
import com.danit.socialnetwork.repository.UserRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.danit.socialnetwork.config.GuavaCache;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danit.socialnetwork.config.GuavaCache.activateCodeCache;
import static com.danit.socialnetwork.config.GuavaCache.userCache;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  @InjectMocks
  UserServiceImpl userService;
  @Mock
  UserRepository userRepository;

  @Mock
  UserFollowRepository userFollowRepository;
  @Mock
  PasswordEncoder passwordEncoder;
  @Mock
  MailSenderImpl mailSender;
  @Mock
  GuavaCache guavaCache;
  @Mock
  SearchDto searchDto;
  @Mock
  SearchMapper searchMapper;

  @Test
  void findByUsername_shouldFindUser_WhenExists() {
    DbUser testDbUser = new DbUser();
    testDbUser.setUsername("Nadya");

    when(userRepository.findByUsername("Nadya")).thenReturn(Optional.of(testDbUser));
    Optional<DbUser> testUser = userService.findByUsername("Nadya");

    Assert.assertEquals(Optional.of(testDbUser), testUser);
  }

  @Test
  public void findByUsername_shouldNotFindUser_WhenNotExists() {
    Optional<DbUser> testUser = userRepository.findByUsername("TestUser");

    Mockito.verify(userRepository).findByUsername("TestUser");


    Assert.assertEquals(Optional.empty(), testUser);
  }

  @Test
  void findById_shouldFindUser_WhenExists() {
    DbUser testDbUser = new DbUser();
    testDbUser.setUserId(28);

    when(userRepository.findById(28)).thenReturn(Optional.of(testDbUser));
    Optional<DbUser> testUser = userService.findById(28);

    Assert.assertEquals(Optional.of(testDbUser), testUser);
  }

  @Test
  void findById_shouldNotFindUser_WhenNotExists() {
    Optional<DbUser> testUser = userRepository.findById(10);

    Mockito.verify(userRepository).findById(10);

    Assert.assertEquals(Optional.empty(), testUser);
  }

  @Test
  void getProfileImage() {
  }

  @Test
  void getBackgroundImage() {
  }

  @Test
  void save_shouldSaveUser_WhenNotExists() {
    DbUser testUser = new DbUser();
    testUser.setUsername("testuser");
    testUser.setPassword("password");
    testUser.setName("Testuser");
    testUser.setEmail("testuser@example.com");
    testUser.setDateOfBirth(LocalDate.of(2000, 01, 01));

    when(userRepository.findByUsername(testUser.getUsername())).thenReturn(Optional.empty());
    when(passwordEncoder.encode(testUser.getPassword())).thenReturn("password");

    boolean result = userService.save(testUser);

    Mockito.verify(userRepository).save(testUser);
    Mockito.verify(passwordEncoder).encode(testUser.getPassword());

    assertTrue(result);
  }

  @Test
  void save_shouldNotSaveUser_WhenExists() {
    DbUser existingUser = new DbUser();
    existingUser.setUsername("Nadya");
    existingUser.setPassword("123");
    existingUser.setName("Nadya");
    existingUser.setEmail("Test@gmail.com");
    existingUser.setDateOfBirth(LocalDate.of(1999, 01, 27));

    when(userRepository.findByUsername(existingUser.getUsername())).thenReturn(Optional.of(existingUser));

    DbUser dbUser = new DbUser();
    dbUser.setUsername("Nadya");
    dbUser.setPassword("123");
    dbUser.setName("Nadya");
    dbUser.setEmail("Test@gmail.com");
    dbUser.setDateOfBirth(LocalDate.of(1999, 01, 27));

    boolean result = userService.save(dbUser);
    Mockito.verify(userRepository, never()).save(Mockito.any(DbUser.class));

    assertFalse(result);
  }

  @Test
  void sendLetter() {
    String name = "Nadya";
    String email = "Test@gmail.com";

    boolean result = userService.sendLetter(name, email);

    assertTrue(result);
  }

  @Test
  void activateUser_WithValidActivationCode() {
    activateCodeCache.put("activationCode", 123456);

    assertTrue(userService.activateUser(123456));
  }

  @Test
  void activateUser_WithInvalidActivationCode() {
    activateCodeCache.put("activationCode", 123456);

    assertFalse(userService.activateUser(654321));
  }

  @Test
  void filterCachedUsersByName_WhenExists() {
    DbUser testDbUser1 = new DbUser();
    testDbUser1.setName("Nadya");
    DbUser testDbUser2 = new DbUser();
    testDbUser2.setName("Nadin");
    DbUser testDbUser3 = new DbUser();
    testDbUser3.setName("Ron");
    DbUser testDbUser4 = new DbUser();
    testDbUser4.setName("Dima");
    DbUser testDbUser5 = new DbUser();
    testDbUser5.setName("Roma");
    DbUser testDbUser6 = new DbUser();
    testDbUser6.setName("Lena");
    DbUser testDbUser7 = new DbUser();
    testDbUser7.setName("Lina");
    DbUser testDbUser8 = new DbUser();
    testDbUser8.setName("Lenok");

    List<DbUser> dbUsers = new ArrayList<>();
    dbUsers.add(testDbUser1);
    dbUsers.add(testDbUser2);
    dbUsers.add(testDbUser3);
    dbUsers.add(testDbUser4);
    dbUsers.add(testDbUser5);
    dbUsers.add(testDbUser6);
    dbUsers.add(testDbUser7);
    dbUsers.add(testDbUser8);
    userCache.put("UserCache", dbUsers);

    List<DbUser> resultSearchDto1 = userService.filterCachedUsersByName("nad");
    List<DbUser> resultSearchDto2 = userService.filterCachedUsersByName("ro");
    List<DbUser> resultSearchDto3 = userService.filterCachedUsersByName("na");

    Assert.assertTrue(resultSearchDto1.size() <= 8);
    Assert.assertTrue(resultSearchDto1.size() == 2);
    Assert.assertTrue(resultSearchDto2.size() <= 8);
    Assert.assertTrue(resultSearchDto2.size() == 2);
    Assert.assertTrue(resultSearchDto3.size() <= 8);
    Assert.assertTrue(resultSearchDto3.size() == 4);
    Assert.assertTrue(resultSearchDto1.get(0).getName().toUpperCase().contains("nad".toUpperCase()));
    Assert.assertTrue(resultSearchDto2.get(0).getName().toUpperCase().contains("ro".toUpperCase()));
    Assert.assertTrue(resultSearchDto3.get(0).getName().toUpperCase().contains("na".toUpperCase()));
  }

  @Test
  void filterCachedUsersByName_WhenNotExists() {
    DbUser testDbUser = new DbUser();
    testDbUser.setName("Nadya");

    List<DbUser> dbUsers = new ArrayList<>();
    dbUsers.add(testDbUser);
    userCache.put("UserCache", dbUsers);

    List<DbUser> testByName = userService.filterCachedUsersByName("nid");

    Assert.assertTrue(testByName.size() == 0);
    Assert.assertFalse(dbUsers.get(0).getName().toUpperCase().contains("nid".toUpperCase()));
  }

  @Test
  void findDbUserByEmail_shouldFindUser_WhenExists() {
    DbUser testDbUser = new DbUser();
    testDbUser.setEmail("Test@gmail.com");

    when(userRepository.findDbUserByEmail("Test@gmail.com")).thenReturn(Optional.of(testDbUser));
    Optional<DbUser> testUser = userService.findDbUserByEmail("Test@gmail.com");

    Assert.assertEquals(Optional.of(testDbUser), testUser);
  }

  @Test
  void findDbUserByEmail_shouldNotFindUser_WhenNotExists() {
    Optional<DbUser> testUser = userRepository.findDbUserByEmail("TestUser@gmail.com");

    Mockito.verify(userRepository).findDbUserByEmail("TestUser@gmail.com");

    Assert.assertEquals(Optional.empty(), testUser);
  }

  @Test
  void findByUserId() {
    Integer userId = 3;
    Integer followers = 3;
    Integer followings = 1;

    DbUser dbUser = new DbUser();
    dbUser.setUserId(userId);
    dbUser.setUsername("John1");
    dbUser.setName("Johny1");
    dbUser.setCreatedDate(LocalDateTime.now());

    when(userRepository.findById(userId)).thenReturn(Optional.of(dbUser));
    when(userFollowRepository.findAllFollowers(userId)).thenReturn(followers);
    when(userFollowRepository.findAllFollowings(userId)).thenReturn(followings);

    UserDtoResponse result = userService.findByUserId(userId);

    Assertions.assertEquals(followers, result.getFollowers());
    Assertions.assertEquals(followings, result.getFollowings());
    Assertions.assertEquals("John1", result.getUsername());
    Assertions.assertEquals("Johny1", result.getName());

  }
}