package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.InboxParticipants;

import java.util.Optional;

public interface InboxParticipantsService {

  Optional<InboxParticipants> findByInboxUidAndUserId(Integer inboxUid, Integer userId);

  InboxParticipants saveInboxParticipantsSender(Integer inboxUid, Integer userId);

  InboxParticipants saveInboxParticipantsReceiver(Integer inboxUid, Integer userId);


}
