package com.danit.socialnetwork.dto.message;

import com.danit.socialnetwork.model.Message;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDtoResponse {
  Integer messageId;
  private Integer inboxUid;
  private Integer userId;
  private String writtenMessage;

  public static MessageDtoResponse from(Message message) {
    MessageDtoResponse messageDto = new MessageDtoResponse();
    messageDto.setMessageId(message.getMessageId());
    messageDto.setInboxUid(message.getInboxUid());
    messageDto.setUserId(message.getUserId());
    messageDto.setWrittenMessage(message.getMessage());
    return messageDto;
  }
}
