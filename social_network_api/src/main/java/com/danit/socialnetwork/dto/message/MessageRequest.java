package com.danit.socialnetwork.dto.message;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Data
public class MessageRequest extends InboxDtoResponse {
  private Integer inboxId;
}
