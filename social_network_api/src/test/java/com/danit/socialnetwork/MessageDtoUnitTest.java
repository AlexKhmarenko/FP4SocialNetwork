import com.danit.socialnetwork.NetworkApp;
import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.MessageGetAllDtoResponse;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Inbox;
import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = NetworkApp.class)
public class MessageDtoUnitTest {
  MessageDtoResponse messageDtoResponse;
  @Mock
  UserService userService;

  @Before
  public void setUp() {
    messageDtoResponse = new MessageDtoResponse();
  }

  @Test
  public void shouldConvertSavedMassageToMessageDtoResponse_Test() {
    Message testMessage = new Message();
    testMessage.setInboxUid(28);
    testMessage.setUserId(34);
    testMessage.setMessage("Hallo world!");
    testMessage.setCreatedAt(null);

    MessageDtoResponse testMessageDto = new MessageDtoResponse();
    testMessageDto.setInboxUid(testMessage.getInboxUid());
    testMessageDto.setUserId(testMessage.getUserId());
    testMessageDto.setWrittenMessage(testMessage.getMessage());
    testMessageDto.setCreatedAt(testMessage.getCreatedAt());

    MessageDtoResponse testMessageConvert = new MessageDtoResponse().from(testMessage);

    Assert.assertEquals(testMessageDto, testMessageConvert);
  }

  @Test
  public void shouldConvertListMessagesToMessageGetAllDtoResponse_Test() {
    Message testMessage1 = new Message(28, 34, "Hallo !!!", null);
    Message testMessage2 = new Message(34, 28, "Hallo )))", null);

    List<Message> testMessages = new ArrayList<>();
    testMessages.add(testMessage1);
    testMessages.add(testMessage2);

    MessageGetAllDtoResponse testMessageResponse1 = new MessageGetAllDtoResponse();
    testMessageResponse1.setInboxUid(28);
    testMessageResponse1.setUserId(34);
    testMessageResponse1.setWrittenMessage("Hallo !!!");
    testMessageResponse1.setCreatedAt(null);
    MessageGetAllDtoResponse testMessageResponse2 = new MessageGetAllDtoResponse();
    testMessageResponse2.setInboxUid(34);
    testMessageResponse2.setUserId(28);
    testMessageResponse2.setWrittenMessage("Hallo )))");
    testMessageResponse2.setCreatedAt(null);

    List<MessageGetAllDtoResponse> testMessagesResponse = new ArrayList<>();
    testMessagesResponse.add(testMessageResponse1);
    testMessagesResponse.add(testMessageResponse2);

    List<MessageGetAllDtoResponse> messagesConvert = new MessageGetAllDtoResponse().from(testMessages);

    Assert.assertEquals(testMessagesResponse, messagesConvert);
  }

  @Test
  public void shouldConvertListInboxesToInboxDtoResponse_Test() throws IOException {
    Inbox testInbox1 = new Inbox();
    testInbox1.setInboxUid(28);
    testInbox1.setLastMessage("Hello, Asy!");
    testInbox1.setLastSentUserId(34);
    testInbox1.setCreatedAt(null);
    Inbox testInbox2 = new Inbox();
    testInbox2.setInboxUid(28);
    testInbox2.setLastMessage("Hello, Sasha!");
    testInbox2.setLastSentUserId(36);
    testInbox2.setCreatedAt(null);

    List<Inbox> testInbox = new ArrayList<>();
    testInbox.add(testInbox1);
    testInbox.add(testInbox2);

    InboxDtoResponse testInboxDtoResponse1 = new InboxDtoResponse();
    testInboxDtoResponse1.setInboxUid(testInbox1.getInboxUid());
    testInboxDtoResponse1.setUsername("Asy");
    testInboxDtoResponse1.setProfileImageUrl(null);
    testInboxDtoResponse1.setWrittenMessage("Hello, Asy!");
    testInboxDtoResponse1.setCreatedAt(null);
    InboxDtoResponse testInboxDtoResponse2 = new InboxDtoResponse();
    testInboxDtoResponse2.setInboxUid(testInbox2.getInboxUid());
    testInboxDtoResponse2.setUsername("AlexX");
    testInboxDtoResponse2.setProfileImageUrl(null);
    testInboxDtoResponse2.setWrittenMessage("Hello, Sasha!");
    testInboxDtoResponse2.setCreatedAt(null);

    List<InboxDtoResponse> testInboxDtoResponse = new ArrayList<>();
    testInboxDtoResponse.add(testInboxDtoResponse1);
    testInboxDtoResponse.add(testInboxDtoResponse2);

    when(userService.findById(34)).thenReturn(Optional.of(new DbUser(
        "Asy", "123", "asy@gmail.com", "Asy", LocalDate.of(2000, 01, 01))));
    when(userService.findById(36)).thenReturn(Optional.of(new DbUser(
        "AlexX","123", "AlexX@gmail.com", "Alex", LocalDate.of(2000, 01, 01))));

    List<InboxDtoResponse> testInboxConvert = InboxDtoResponse.from(testInbox, userService);

    Assert.assertEquals(testInboxDtoResponse, testInboxConvert);
  }

}