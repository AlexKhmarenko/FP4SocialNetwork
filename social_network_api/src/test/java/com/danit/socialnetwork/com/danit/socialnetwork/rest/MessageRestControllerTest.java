package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.dto.message.InboxParticipantsDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.search.MessageSearchDto;
import com.danit.socialnetwork.dto.search.SearchRequest;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.service.InboxService;
import com.danit.socialnetwork.service.MessageService;
import com.danit.socialnetwork.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MessageRestControllerTest {
  @Mock
  MessageService messageService;
  @Mock
  InboxService inboxService;
  @Mock
  UserService userService;
  @InjectMocks
  private MessageRestController controller;
  private MockMvc mockMvc;

  @BeforeEach
  public void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
  }

  @Test
  void addMessage() throws Exception {
    DbUser testUser1 = new DbUser();
    testUser1.setUserId(1);
    DbUser testUser2 = new DbUser();
    testUser2.setUserId(2);
    String message = "Hello World!";

    MessageDtoRequest request = new MessageDtoRequest();
    request.setInboxUid(1);
    request.setUserId(2);
    request.setWrittenMessage(message);

    mockMvc.perform(post("/api/addMessage")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isCreated());

    verify(messageService).saveMessage(request, "Europe/London");
  }

  @Test
  void getInbox() throws Exception {
    Integer inboxUidTest = 1;
    List<InboxDtoResponse> testInboxDto = new ArrayList<>();

    when(inboxService.getInboxesByInboxUid(inboxUidTest, "Europe/London")).thenReturn(testInboxDto);

    mockMvc.perform(get("/api/1/inbox")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(inboxUidTest)))
        .andExpect(status().isFound());

    verify(inboxService).getInboxesByInboxUid(inboxUidTest, "Europe/London");
  }

  @Test
  void getMessage() throws Exception {
    InboxParticipantsDtoRequest request = new InboxParticipantsDtoRequest();
    request.setInboxUid(1);
    request.setUserId(2);
    List<MessageDtoResponse> testMessageDto = new ArrayList<>();
    Integer page = 0;
    when(messageService.findByInboxUidAndUserIdOrUserIdAndInboxUid(request, page, "Europe/London")).thenReturn(testMessageDto);

    mockMvc.perform(post("/api/getMessages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isFound());

    verify(messageService).findByInboxUidAndUserIdOrUserIdAndInboxUid(request, page, "Europe/London");
  }

  @Test
  void readMessage() throws Exception {
    MessageDtoRequest request = new MessageDtoRequest();
    request.setInboxUid(1);
    request.setUserId(2);

    DbUser testUserSender1 = new DbUser();
    testUserSender1.setUserId(1);
    testUserSender1.setName("TestUser1");
    testUserSender1.setUsername("TestUser1");
    DbUser testUserReceiver1 = new DbUser();
    testUserReceiver1.setUserId(2);
    testUserReceiver1.setName("TestUser2");
    testUserReceiver1.setUsername("TestUser2");

    when(userService.findDbUserByUserId(1)).thenReturn(testUserSender1);
    when(userService.findDbUserByUserId(2)).thenReturn(testUserReceiver1);

    mockMvc.perform(post("/api/readMessages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isOk());

    verify(messageService).unreadToReadMessages(testUserSender1, testUserReceiver1);
  }

  @Test
  void handleMessageSearchPost() throws Exception {
    String StringSearch = "hel";
    SearchRequest request = new SearchRequest();
    request.setSearch(StringSearch);

    MessageSearchDto messageSearchDto1 = new MessageSearchDto();
    messageSearchDto1.setMessage("hello");
    MessageSearchDto messageSearchDto2 = new MessageSearchDto();
    messageSearchDto2.setMessage("hello");
    List<MessageSearchDto> messageSearchDto = new ArrayList<>();
    messageSearchDto.add(messageSearchDto1);
    messageSearchDto.add(messageSearchDto2);

    when(messageService.filterCachedMessageByString(request, "Europe/London")).thenReturn(messageSearchDto);

    mockMvc.perform(post("/api/messageSearch")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isFound());
  }

  @Test
  void addInbox() throws Exception {
    DbUser testUser1 = new DbUser();
    testUser1.setUserId(1);
    DbUser testUser2 = new DbUser();
    testUser2.setUserId(2);

    InboxParticipantsDtoRequest request = new InboxParticipantsDtoRequest();
    request.setInboxUid(1);
    request.setUserId(2);

    mockMvc.perform(post("/api/addInbox")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(request)))
        .andExpect(status().isCreated());

    verify(inboxService).addInbox(request, "Europe/London");
  }

}