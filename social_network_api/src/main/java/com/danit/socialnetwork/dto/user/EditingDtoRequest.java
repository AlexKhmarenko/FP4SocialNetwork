package com.danit.socialnetwork.dto.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Min;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class EditingDtoRequest {
  @NotNull(message = "number required")
  @Positive(message = "positive number required")
  private Integer userId;
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
  @NotBlank(message = "address is required")
  @Size(max = 100, message = "address cannot exceed 20 characters")
  private String address;
  private byte [] profileBackgroundImageUrl;
  private byte [] profileImageUrl;
  @URL
  private String profileBackgroundImageUrlString;
  @URL
  private String profileImageUrlString;

}
