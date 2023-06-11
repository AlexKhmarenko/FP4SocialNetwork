package com.danit.socialnetwork.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Log4j2
@Data
public class UserEmailForLoginRequest {
  @NotBlank(message = "email is required")
  @Email(message = "invalid email format")
  private String email;
}
