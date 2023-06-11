package com.danit.socialnetwork.dto;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserEmailRequest {
  @NotBlank(message = "name is required")
  @Size(max = 20, message = "name cannot exceed 20 characters")
  private String name;
  @NotBlank(message = "email is required")
  @Email(message = "invalid email format")
  private String email;
}
