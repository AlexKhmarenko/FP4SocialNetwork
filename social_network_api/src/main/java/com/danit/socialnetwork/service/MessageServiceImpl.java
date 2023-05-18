package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class MessageServiceImpl implements MessageService {

  private final MessageRepository messageRepository;
  private final InboxService inboxService;
  private final InboxParticipantsService inboxParticipantsService;

  @Override
  public Message saveMessage(MessageDtoRequest messageDtoRequest) {
    Integer inboxUid = messageDtoRequest.getInboxUid();
    Integer userId = messageDtoRequest.getUserId();
    String writtenMessage = messageDtoRequest.getWrittenMessage();

    Message message = new Message();
    message.setInboxUid(inboxUid);
    message.setUserId(userId);
    message.setMessage(writtenMessage);

    Message savedMessage = messageRepository.save(message);
    log.info(String.format("Save message: setInboxUid = %d, setUserId = %d, setMessage = %s",
        savedMessage.getInboxUid(), savedMessage.getUserId(), savedMessage.getMessage()));

    LocalDateTime createdAt = savedMessage.getCreatedAt();

    inboxService.saveInboxSender(inboxUid, userId, writtenMessage, createdAt);
    inboxService.saveInboxReceiver(inboxUid, userId, writtenMessage, createdAt);

    inboxParticipantsService.saveInboxParticipantsSender(inboxUid, userId);
    inboxParticipantsService.saveInboxParticipantsReceiver(inboxUid, userId);

    return savedMessage;
  }

  @Override
  public List<Message> findByInboxUidAndUserIdOrUserIdAndInboxUid(
      Integer inboxUid, Integer userId, Integer inboxUidIncoming, Integer userIdIncoming) {
    return messageRepository.findByInboxUidAndUserIdOrUserIdAndInboxUid(inboxUid, userId, inboxUid, userId);
  }

}
