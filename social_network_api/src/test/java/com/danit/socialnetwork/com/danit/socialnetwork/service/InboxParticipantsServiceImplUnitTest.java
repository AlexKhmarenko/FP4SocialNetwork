package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.InboxParticipants;
import com.danit.socialnetwork.repository.InboxParticipantsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class InboxParticipantsServiceImplUnitTest {
  @Mock
  InboxParticipantsRepository inboxParticipantsRepository;
  InboxParticipantsService inboxParticipantsService;

  @Before
  public void setUp() {
    inboxParticipantsService = new InboxParticipantsServiceImpl(inboxParticipantsRepository);
  }

  @Test
  public void findByInboxUidAndUserId_shouldFindInboxParticipants_Test() {
    InboxParticipants testInboxParticipants = new InboxParticipants();
    testInboxParticipants.setInboxUid(28);
    testInboxParticipants.setUserId(34);

    when(inboxParticipantsRepository.findByInboxUidAndUserId(28, 34))
        .thenReturn(Optional.of(testInboxParticipants));

    Optional<InboxParticipants> testFindInboxParticipants = inboxParticipantsService
        .findByInboxUidAndUserId(28, 34);

    Mockito.verify(inboxParticipantsRepository).findByInboxUidAndUserId(28, 34);

    Assert.assertEquals(Optional.of(testInboxParticipants), testFindInboxParticipants);
  }

  @Test
  public void findByInboxUidAndUserId_shouldFindInboxParticipants_WhenNotExists_Test() {
    Optional<InboxParticipants> testFindInboxParticipants = inboxParticipantsService
        .findByInboxUidAndUserId(28, 34);

    Mockito.verify(inboxParticipantsRepository).findByInboxUidAndUserId(28, 34);

    Assert.assertEquals(Optional.empty(), testFindInboxParticipants);
  }

  @Test
  public void saveInboxParticipants_shouldSaveInboxParticipants_WhenNotExists() {
    InboxParticipants testInboxParticipants = new InboxParticipants();
    testInboxParticipants.setInboxUid(10);
    testInboxParticipants.setUserId(11);

    when(inboxParticipantsService.findByInboxUidAndUserId(10, 11))
        .thenReturn(Optional.empty());

    InboxParticipants testSaveInboxParticipants = inboxParticipantsService
        .saveInboxParticipants(10, 11);
    Mockito.verify(inboxParticipantsRepository).findByInboxUidAndUserId(10, 11);
    Mockito.verify(inboxParticipantsRepository).save(testSaveInboxParticipants);

    Assert.assertEquals(testInboxParticipants, testSaveInboxParticipants);
  }

}