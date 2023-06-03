package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.message.InboxParticipantsDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.search.MessageSearchDto;
import com.danit.socialnetwork.dto.search.SearchRequest;
import com.danit.socialnetwork.exception.user.UserNotFoundException;
import com.danit.socialnetwork.mappers.MessageMapper;
import com.danit.socialnetwork.mappers.MessageSearchMapper;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.repository.MessageRepository;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.danit.socialnetwork.config.GuavaCache.messageCache;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final UserRepository userRepository;
  private final MessageSearchMapper searchMapper;
  private final InboxServiceImpl inboxService;
  private final MessageMapper messageMapper;

  /*Method save a new message and returns it*/
  @Override
  public MessageDtoResponse saveMessage(MessageDtoRequest request) {
    Integer userSenderId = request.getInboxUid();
    Optional<DbUser> userSender = userRepository.findById(userSenderId);
    DbUser userS = null;
    if (userSender.isPresent()) {
      userS = userSender.get();
    } else {
      throw new UserNotFoundException(String.format("User with userId %d not found", userSenderId));
    }

    Integer userReceiverId = request.getUserId();
    Optional<DbUser> userReceiver = userRepository.findById(userReceiverId);
    DbUser userR = null;
    if (userReceiver.isPresent()) {
      userR = userReceiver.get();
    } else {
      throw new UserNotFoundException(String.format("User with userId %d not found", userReceiverId));
    }

    String writtenMessage = request.getWrittenMessage();

    Message message = new Message();
    message.setMessageText(writtenMessage);
    message.setInboxUid(userS);
    message.setUserId(userR);
    message.setMessageReade(false);
    Message savedMessage = messageRepository.save(message);
    log.info(String.format("Save message: %s", savedMessage.getMessageText()));

    inboxService.saveInbox(userS, userR, message);

    return messageMapper.messageToMessageDtoResponse(savedMessage);
  }

  /*The method finds all messages between the sender and the receiver and returns them*/
  @Override
  public List<MessageDtoResponse> findByInboxUidAndUserIdOrUserIdAndInboxUid(InboxParticipantsDtoRequest request) {
    DbUser inboxUid = userRepository.findById(request.getInboxUid()).get();
    DbUser userId = userRepository.findById(request.getUserId()).get();
    List<Message> messages = messageRepository
        .findByInboxUidAndUserIdOrUserIdAndInboxUid(inboxUid, userId, inboxUid, userId);
    return messages.stream().map(m -> messageMapper.messageToMessageDtoResponse(m)).toList();
  }

  /*The method finds all incoming and outgoing messages of the user and returns them*/
  @Override
  public List<Message> findMessageByInboxUidOrUserId(DbUser inboxUid, DbUser userId) {
    return messageRepository.findMessageByInboxUidOrUserId(userId, userId);
  }

  /*The method writes all messages to cache if there is no cache,
   and filters messages from cache by requested string. And returns them*/
  @Override
  public List<MessageSearchDto> filterCachedMessageByString(SearchRequest request) throws UserNotFoundException {
    List<MessageSearchDto> search = new ArrayList<>();
    Integer userId = Integer.valueOf(request.getUserId());
    Optional<DbUser> oUserFromDb = userRepository.findById(userId);
    if (oUserFromDb.isEmpty()) {
      new UserNotFoundException(String.format("User with userId %d not found", userId));
    } else {
      DbUser userFromDb = oUserFromDb.get();
      String messageSearch = request.getSearch();

      if (messageCache.getIfPresent("MessageCache") == null) {
        List<Message> cacheMessage = messageRepository.findMessageByInboxUidOrUserId(userFromDb, userFromDb);
        log.debug(String.format("cacheMessage.size = %d", cacheMessage.size()));
        messageCache.put("MessageCache", cacheMessage);
      }
      log.debug(String.format("filterCachedMessagesByString: %s. "
          + "Should find all messages by string.", messageSearch));
      search = messageCache.getIfPresent("MessageCache").stream()
          .filter(m -> m.getMessageText().toLowerCase().contains(messageSearch.toLowerCase()))
          .map(mf -> searchMapper.messageToMessageSearchDto(mf)).toList();
      log.debug(String.format("filterCachedMessageByString: %s. Find all Messages by string.", messageSearch));
    }
    return search;
  }

}
