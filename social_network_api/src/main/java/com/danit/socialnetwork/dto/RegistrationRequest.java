package com.danit.socialnetwork.dto;

import lombok.Data;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationRequest {
  @NotBlank(message = "username is required")
  @Size(max = 20, message = "username cannot exceed 20 characters")
  private String username;
  @NotBlank(message = "password is required")
  @Size(min = 7, message = "password must have at least 7 characters")
  private String password;
  @NotBlank(message = "email is required")
  @Email(message = "invalid email format")
  private String email;
  @NotBlank(message = "name is required")
  @Size(max = 20, message = "name cannot exceed 20 characters")
  private String name;
  @Min(1)
  @Max(31)
  @NotNull(message = "day is required")
  private Integer day;
  @Min(1)
  @Max(12)
  @NotNull(message = "month is required")
  private Integer month;
  @Min(1900)
  @Max(2100)
  @NotNull(message = "year is required")
  private Integer year;

}
