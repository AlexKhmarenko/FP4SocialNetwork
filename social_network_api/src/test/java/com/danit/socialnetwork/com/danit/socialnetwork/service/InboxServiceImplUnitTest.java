package com.danit.socialnetwork.service;

import com.danit.socialnetwork.NetworkApp;
import com.danit.socialnetwork.model.Inbox;
import com.danit.socialnetwork.repository.InboxRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(classes = NetworkApp.class)
public class InboxServiceImplUnitTest {
  @Mock
  InboxRepository inboxRepository;
  InboxService inboxService;

  @Before
  public void setUp() {
    inboxService = new InboxServiceImpl(inboxRepository);
  }

  @Test
  public void findByInboxUidAndLastSentUserId_shouldFindInbox_Test() {
    Inbox testInbox = new Inbox();
    testInbox.setInboxUid(28);
    testInbox.setLastSentUserId(34);

    when(inboxRepository.findByInboxUidAndLastSentUserId(28, 34))
        .thenReturn(Optional.of(testInbox));

    Optional<Inbox> testFindInbox = inboxService.findByInboxUidAndLastSentUserId(28, 34);
    Mockito.verify(inboxRepository).findByInboxUidAndLastSentUserId(28, 34);

    Assert.assertEquals(Optional.of(testInbox), testFindInbox);
  }

  @Test
  public void findByInboxUidAndLastSentUserId_shouldFindInbox_WhenNotExists_Test() {
    Optional<Inbox> testFindInbox = inboxService.findByInboxUidAndLastSentUserId(10, 11);

    Mockito.verify(inboxRepository).findByInboxUidAndLastSentUserId(10, 11);

    Assert.assertEquals(Optional.empty(), testFindInbox);
  }

  @Test
  public void saveInbox_shouldSaveInbox_WhenNotExists() {
    Inbox testInbox = new Inbox();
    testInbox.setInboxUid(10);
    testInbox.setLastMessage("Hello World!");
    testInbox.setLastSentUserId(11);

    when(inboxRepository.findByInboxUidAndLastSentUserId(10, 11))
        .thenReturn(Optional.empty());

    Inbox testSaveInbox = inboxService
        .saveInbox(10, 11, "Hello World!", null);
    Mockito.verify(inboxRepository).findByInboxUidAndLastSentUserId(10, 11);
    Mockito.verify(inboxRepository).save(testSaveInbox);

    Assert.assertEquals(testInbox, testSaveInbox);
  }

  @Test
  public void saveInbox_shouldSaveInbox_WhenExists() {
    Inbox existingInbox = new Inbox();
    existingInbox.setInboxUid(28);
    existingInbox.setLastMessage("Hello World!");
    existingInbox.setLastSentUserId(34);
    existingInbox.setCreatedAt(LocalDateTime.now());

    when(inboxRepository.findByInboxUidAndLastSentUserId(28, 34))
        .thenReturn(Optional.of(existingInbox));

    Inbox testSaveInboxWhenExists = inboxService
        .saveInbox(28, 34, "Test message!", LocalDateTime.now());

    Mockito.verify(inboxRepository).save(testSaveInboxWhenExists);

    Assert.assertNotNull(testSaveInboxWhenExists.getLastMessage());
    Assert.assertNotNull(testSaveInboxWhenExists.getCreatedAt());
    Assert.assertEquals(existingInbox.getInboxUid(), testSaveInboxWhenExists.getInboxUid());
    Assert.assertEquals(existingInbox.getLastSentUserId(), testSaveInboxWhenExists.getLastSentUserId());
    Assert.assertEquals("Test message!", testSaveInboxWhenExists.getLastMessage());
    Assert.assertNotEquals("Hello World!", testSaveInboxWhenExists.getLastMessage());
  }

  @Test
  public void getInboxesByInboxUid_shouldGetAllInboxesByInboxUid_Test() {
    Inbox inbox1 = new Inbox();
    inbox1.setInboxUid(28);
    inbox1.setLastMessage("Hello, Asy!");
    inbox1.setLastSentUserId(34);
    inbox1.setCreatedAt(LocalDateTime.now());
    Inbox inbox2 = new Inbox();
    inbox2.setInboxUid(28);
    inbox2.setLastMessage("Hello, Sasha!");
    inbox2.setLastSentUserId(36);
    inbox2.setCreatedAt(LocalDateTime.now());

    List<Inbox> testInbox = new ArrayList<>();
    testInbox.add(inbox1);
    testInbox.add(inbox2);

    when(inboxRepository.getInboxesByInboxUid(28)).thenReturn(testInbox);

    List<Inbox> testFindInbox = inboxService.getInboxesByInboxUid(28);

    Assert.assertEquals(testInbox, testFindInbox);
  }

}