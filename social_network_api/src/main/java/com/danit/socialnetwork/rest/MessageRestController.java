package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.message.InboxParticipantsDtoRequest;
import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.dto.message.search.MessageSearchDto;
import com.danit.socialnetwork.dto.search.SearchDto;
import com.danit.socialnetwork.dto.search.SearchRequest;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.service.InboxService;
import com.danit.socialnetwork.service.MessageService;
import com.danit.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MessageRestController {
  private final MessageService messageService;
  private final InboxService inboxService;
  private final UserService userService;

  /*Method save a new message*/
  @PostMapping(path = "/addMessage")
  public ResponseEntity<MessageDtoResponse> addMessage(
      @RequestBody MessageDtoRequest request,
      @RequestParam(value = "time_zone",
          defaultValue = "Europe/London") String userTimeZone) {
    MessageDtoResponse dbMessage = messageService.saveMessage(request, userTimeZone);
    return new ResponseEntity<>(dbMessage, HttpStatus.CREATED);
  }

  /*The method finds inbox by message sender and receiver */
  @GetMapping(path = "/{inboxUid}/inbox")
  public ResponseEntity<List<InboxDtoResponse>> getInbox(
      @PathVariable("inboxUid") Integer inboxUid,
      @RequestParam(value = "time_zone",
          defaultValue = "Europe/London") String userTimeZone) {
    List<InboxDtoResponse> inboxes = inboxService.getInboxesByInboxUid(inboxUid, userTimeZone);
    return new ResponseEntity<>(inboxes, HttpStatus.FOUND);
  }

  /*The method finds all messages between the sender and the receiver*/
  @PostMapping(path = "/getMessages")
  public ResponseEntity<List<MessageDtoResponse>> getMessage(
      @RequestBody InboxParticipantsDtoRequest request,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(value = "time_zone",
          defaultValue = "Europe/London") String userTimeZone) {
    List<MessageDtoResponse> messages = messageService
        .findByInboxUidAndUserIdOrUserIdAndInboxUid(request, page, userTimeZone);
    return new ResponseEntity<>(messages, HttpStatus.FOUND);
  }

  /*The method convert messages to the read messages*/
  @PostMapping(path = "/readMessages")
  public void readMessage(@RequestBody MessageDtoRequest request) {
    DbUser userS = userService.findDbUserByUserId(request.getInboxUid());
    DbUser userR = userService.findDbUserByUserId(request.getUserId());
    messageService.unreadToReadMessages(userS, userR);
  }

  /*The method writes all messages to cache if there is no cache,
   and filters messages from cache by requested string*/
  @PostMapping(path = "/messageSearch")
  public ResponseEntity<Object> handleMessageSearchPost(
      @RequestBody SearchRequest request,
      @RequestParam(value = "time_zone",
          defaultValue = "Europe/London") String userTimeZone) {
    List<MessageSearchDto> messageSearchDto = messageService.filterCachedMessageByString(request, userTimeZone);
    if (messageSearchDto.isEmpty()) {
      List<SearchDto> searchDto = userService.filterCachedUsersByName(request);
      return new ResponseEntity<>(searchDto, HttpStatus.FOUND);
    }
    return new ResponseEntity<>(messageSearchDto, HttpStatus.FOUND);
  }

  /*Method save a new inbox*/
  @PostMapping(path = "/addInbox")
  public ResponseEntity<InboxDtoResponse> addInbox(
      @RequestBody InboxParticipantsDtoRequest request,
      @RequestParam(value = "time_zone",
          defaultValue = "Europe/London") String userTimeZone) {
    InboxDtoResponse dbInbox = inboxService.addInbox(request, userTimeZone);
    return new ResponseEntity<>(dbInbox, HttpStatus.CREATED);
  }

  @GetMapping(path = "/{inboxUid}/unread")
  public ResponseEntity<Map<String, Integer>> numUnreadMessages(
      @PathVariable("inboxUid") Integer inboxUid) {
    Integer numUnreadMessages = messageService.numberUnreadMessages(inboxUid);
    Map<String, Integer> response = new HashMap<>();
    response.put("unread", numUnreadMessages);
    return ResponseEntity.ok(response);
  }

}
