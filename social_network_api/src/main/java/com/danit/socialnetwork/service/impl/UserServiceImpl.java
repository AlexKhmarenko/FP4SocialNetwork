package com.danit.socialnetwork.service.impl;

import com.danit.socialnetwork.GuavaCache;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.DbUserRepo;
import com.danit.socialnetwork.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Log4j2
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

  private final DbUserRepo dbUserRepo;
  private final PasswordEncoder enc;
  private final MailSenderImpl mailSender;
  private final GuavaCache guavaCache;

  public Optional<DbUser> findByUsername(String username) {
    return dbUserRepo.findByUsername(username);
  }

  public boolean save(DbUser dbUser) {
    Optional<DbUser> userFromDb = dbUserRepo.findByUsername(dbUser.getUsername());

    if (userFromDb.isPresent()) {
      log.info("User exists!");
      return false;
    }

    dbUser.setPassword(enc.encode(dbUser.getPassword()));
    dbUserRepo.save(dbUser);
    log.info(String.format("save user name = %s, email = %s",
        dbUser.getName(), dbUser.getEmail()));
    return true;
  }

  @Override
  public boolean sendLetter(String name, String email) {

    Random rand = new Random();
    int randomNumber = rand.nextInt(900000) + 100000;

    guavaCache.put("activationCode", randomNumber);

    try {
      String message = String.format(
          "Hello, %s! \n " +
              "Welcome to BlitzPost. Email confirmation code %s",
          name, randomNumber);
      log.info(String.format(message));
      mailSender.send(email, "Activation code", message);
      log.info(String.format("mail Send to user name = %s, email = %s ", name, email));
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public boolean activateUser(Integer code) {
    Integer activationCode = guavaCache.getUnchecked("activationCode");
    if (activationCode == null) return false;

    return code.equals(activationCode);
  }

}

