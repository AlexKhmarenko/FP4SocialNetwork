package com.danit.socialnetwork.service;

import com.danit.socialnetwork.dto.message.InboxParticipantsDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoRequest;
import com.danit.socialnetwork.dto.message.MessageDtoResponse;
import com.danit.socialnetwork.dto.message.search.MessageSearchDto;
import com.danit.socialnetwork.dto.search.SearchRequest;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.model.Message;

import java.util.List;

public interface MessageService {

  MessageDtoResponse saveMessage(MessageDtoRequest request);

  List<MessageDtoResponse> findByInboxUidAndUserIdOrUserIdAndInboxUid(InboxParticipantsDtoRequest request);

  List<Message> findMessageByInboxUidOrUserId(DbUser inboxUid, DbUser userId);

  List<MessageSearchDto> filterCachedMessageByString(SearchRequest request);
}