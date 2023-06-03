package com.danit.socialnetwork.rest;

import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.InboxDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.message.InboxParticipantsDtoRequest;
import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.dto.message.search.MessageSearchDto;
import com.danit.socialnetwork.dto.search.SearchDto;
import com.danit.socialnetwork.dto.search.SearchRequest;
import com.danit.socialnetwork.mappers.SearchMapper;
import com.danit.socialnetwork.service.InboxService;
import com.danit.socialnetwork.service.MessageService;
import com.danit.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Log4j2
@RestController
@RequiredArgsConstructor
public class MessageRestController {
  private final MessageService messageService;
  private final InboxService inboxService;
  private final UserService userService;
  private final SearchMapper searchMapper;

  /*Method save a new message*/
  @PostMapping(path = "/api/message")
  public ResponseEntity<MessageDtoResponse> addMessage(@RequestBody MessageDtoRequest request) {
    MessageDtoResponse dbMessage = messageService.saveMessage(request);
    return new ResponseEntity<>(dbMessage, HttpStatus.CREATED);
  }

  /*The method finds inbox by message sender and receiver */
  @GetMapping(path = "/api/inbox")
  public ResponseEntity<List<InboxDtoResponse>> getInbox(@RequestBody InboxDtoRequest request) {
    List<InboxDtoResponse> inboxes =  inboxService.getInboxesByInboxUid(request);
    return new ResponseEntity<>(inboxes, HttpStatus.FOUND);
  }

  /*The method finds all messages between the sender and the receiver*/
  @GetMapping(path = "/api/message")
  public ResponseEntity<List<MessageDtoResponse>> getMessage(
      @RequestBody InboxParticipantsDtoRequest request) {
    List<MessageDtoResponse> messages =  messageService
        .findByInboxUidAndUserIdOrUserIdAndInboxUid(request);
    return new ResponseEntity<>(messages, HttpStatus.FOUND);
  }

  /*The method writes all messages to cache if there is no cache,
   and filters messages from cache by requested string*/
  @RequestMapping(value = "/api/messageSearch", method = RequestMethod.POST)
  public ResponseEntity<?> handleMessageSearchPost(@RequestBody SearchRequest request) {
    List<MessageSearchDto> messageSearchDto = messageService.filterCachedMessageByString(request);
    if (messageSearchDto.isEmpty()) {
      List<SearchDto> searchDto = userService.filterCachedUsersByName(request);
      return new ResponseEntity<>(searchDto, HttpStatus.FOUND);
    }
    return new ResponseEntity<>(messageSearchDto, HttpStatus.FOUND);
  }

}