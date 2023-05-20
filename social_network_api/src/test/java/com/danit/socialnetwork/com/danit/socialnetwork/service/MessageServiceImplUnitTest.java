package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.repository.MessageRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ActiveProfiles("test")
@RunWith(MockitoJUnitRunner.class)
public class MessageServiceImplUnitTest {
  @Mock
  MessageRepository messageRepository;
  MessageService messageService;

  @Before
  public void setUp() {
    messageService = new MessageServiceImpl(messageRepository);
  }

  @Test
  public void saveMessage_Test() {
    Message testMessage = new Message();
    testMessage.setInboxUid(28);
    testMessage.setUserId(34);
    testMessage.setMessage("Hallo world!");

    when(messageRepository.save(testMessage)).thenReturn(testMessage);

    Message savedMessage = messageService.saveMessage(testMessage);
    Mockito.verify(messageRepository).save(testMessage);

    Assert.assertEquals(testMessage, savedMessage);
  }

  @Test
  public void findByInboxUidAndUserIdOrUserIdAndInboxUid_shouldFindMessages_Test() {
    Message message1 = new Message();
    message1.setInboxUid(28);
    message1.setUserId(34);
    message1.setMessage("Hello");
    Message message2 = new Message();
    message2.setMessage("World");
    message2.setInboxUid(34);
    message2.setUserId(28);

    List<Message> testMessages = new ArrayList<>();
    testMessages.add(message1);
    testMessages.add(message2);

    when(messageRepository
        .findByInboxUidAndUserIdOrUserIdAndInboxUid(28, 34, 28, 34))
        .thenReturn(testMessages);

    List<Message> testFindMessages = messageService
        .findByInboxUidAndUserIdOrUserIdAndInboxUid(28, 34, 28, 34);

    Assert.assertEquals(testMessages, testFindMessages);
  }

}