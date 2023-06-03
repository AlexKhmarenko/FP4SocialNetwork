package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.message.InboxDtoRequest;
import com.danit.socialnetwork.dto.message.InboxDtoResponse;
import com.danit.socialnetwork.exception.user.UserNotFoundException;
import com.danit.socialnetwork.mappers.InboxMapperImpl;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Inbox;
import com.danit.socialnetwork.model.Message;
import com.danit.socialnetwork.repository.InboxRepository;
import com.danit.socialnetwork.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Log4j2
public class InboxServiceImpl implements InboxService {
  private final InboxRepository inboxRepository;
  private final UserRepository userRepository;
  private final InboxMapperImpl mapper;

  /*The method finds inbox by message sender and receiver and returns it*/
  @Override
  public Optional<Inbox> findByInboxUidAndLastSentUserId(DbUser inboxUid, DbUser lastSentUserId) {
    return inboxRepository.findByInboxUidAndUserId(inboxUid, lastSentUserId);
  }

  /*The method saves a new inbox if it does not exist and updates it if it does exist and returns it*/
  @Override
  public List<Inbox> saveInbox(DbUser senderId, DbUser receiverId, Message message) {
    List<Inbox> inboxesSenderAndReceiver = new ArrayList<>();

    Optional<Inbox> inboxSender = inboxRepository.findByInboxUidAndUserId(senderId, receiverId);
    if (inboxSender.isEmpty()) {
      Inbox inboxNewSender = new Inbox(senderId, receiverId, message);
      Inbox inboxS = inboxRepository.save(inboxNewSender);
      Inbox inboxNewReceiver = new Inbox(receiverId, senderId, message);
      Inbox inboxR = inboxRepository.save(inboxNewReceiver);
      inboxesSenderAndReceiver.add(inboxS);
      inboxesSenderAndReceiver.add(inboxR);
    } else {
      Optional<Inbox> inboxFromDbRo = inboxRepository.findByInboxUidAndUserId(receiverId, senderId);
      if (inboxFromDbRo.isPresent()) {
        Inbox inboxFromDbS = inboxSender.get();
        inboxFromDbS.setLastMessage(message);
        Inbox InboxFromDbR = inboxFromDbRo.get();
        InboxFromDbR.setLastMessage(message);
        Inbox inboxS = inboxRepository.save(inboxFromDbS);
        Inbox inboxR = inboxRepository.save(InboxFromDbR);
        inboxesSenderAndReceiver.add(inboxS);
        inboxesSenderAndReceiver.add(inboxR);
      }
    }
    return inboxesSenderAndReceiver;
  }

  /*The method finds the inbox by sender and returns it*/
  @Override
  public List<InboxDtoResponse> getInboxesByInboxUid(InboxDtoRequest request) {
    Integer userId = request.getInboxUid();
    List<InboxDtoResponse> inboxesDto;
    Optional<DbUser> oInboxUid = userRepository.findById(userId);
    if (oInboxUid.isEmpty()) {
      throw new UserNotFoundException(String.format("User with userId %s not found", userId));
    } else {
      List<Inbox> inboxes = inboxRepository.getInboxesByInboxUid(oInboxUid.get());
      inboxesDto = inboxes.stream()
          .map(mapper::inboxToInboxDtoResponse).toList();
    }
    return inboxesDto;
  }

}
