package com.danit.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailSenderImpl implements MailSender {

  private final JavaMailSender mailSender;
  @Value("${spring.mail.username}")
  private String username;

  public void send(String emailTo, String subject, String message) {
    SimpleMailMessage mailMessage = new SimpleMailMessage();

    mailMessage.setFrom(username);
    mailMessage.setTo(emailTo);
    mailMessage.setSubject(subject);
    mailMessage.setText(message);

    mailSender.send(mailMessage);
  }

  @Override
  public void send(SimpleMailMessage simpleMessage) throws MailException {

  }

  @Override
  public void send(SimpleMailMessage... simpleMessages) throws MailException {

  }
}
