package com.danit.socialnetwork.exception;

import com.danit.socialnetwork.exception.user.AppUserError;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GlobalExceptionHandlerTest {

  @Test
  public void testCatchNotFoundException_ReturnsNotFoundResponse() {
    // Arrange
    GlobalExceptionHandler globalExceptionHandler = new GlobalExceptionHandler();
    Exception exception = new AppUserError("User not found");

    // Act
    ResponseEntity<ErrorResponse> response = globalExceptionHandler.catchNotFoundException(exception);

    // Assert
    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    ErrorResponse errorResponse = response.getBody();
    assertEquals(HttpStatus.NOT_FOUND.value(), errorResponse.getStatus());
    assertEquals("User not found", errorResponse.getMessage());
    // Add additional assertions as necessary
  }


}
