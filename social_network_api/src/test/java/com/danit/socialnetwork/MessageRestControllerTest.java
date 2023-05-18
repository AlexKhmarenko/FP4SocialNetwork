import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.repository.InboxRepository;
import com.danit.socialnetwork.rest.MessageRestController;
import com.danit.socialnetwork.service.InboxParticipantsService;
import com.danit.socialnetwork.service.InboxService;
import com.danit.socialnetwork.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class MessageRestControllerTest {

  private MockMvc mockMvc;

  @Mock
  private MessageService messageService;

  @Mock
  private InboxService inboxService;

  @Mock
  private InboxRepository inboxRepository;

  @Mock
  private InboxParticipantsService inboxParticipantsService;

  @InjectMocks
  private MessageRestController controller;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  public void testAddMessage_Post() throws Exception {
    Integer inboxUid = 28;
    Integer userId = 34;
    String message = "Hello World!";

    MessageDtoRequest messageDtoRequest = new MessageDtoRequest();
    messageDtoRequest.setInboxUid(inboxUid);
    messageDtoRequest.setUserId(userId);
    messageDtoRequest.setWrittenMessage(message);

    Message testSaveMessage = new Message();
    testSaveMessage.setInboxUid(inboxUid);
    testSaveMessage.setUserId(userId);
    testSaveMessage.setMessage(message);

    when(messageService.saveMessage(any(Message.class))).thenReturn(testSaveMessage);

    mockMvc.perform(post("/message")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(messageDtoRequest)))
        .andExpect(status().isCreated());

    verify(messageService).saveMessage(testSaveMessage);
  }

}