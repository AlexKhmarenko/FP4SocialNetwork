package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.DbUserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Log4j2
@Service
@RequiredArgsConstructor
public class DbUserService {

  private final DbUserRepo dbUserRepo;
  private final PasswordEncoder enc;

  private final MailSender mailSender;

  public List findAll() {
    return dbUserRepo.findAll();
  }

  public Optional<DbUser> findByUsername(String username) {
    return dbUserRepo.findByUsername(username);
  }

  public boolean saveUser(DbUser dbUser) {
    Optional<DbUser> userFromDb = dbUserRepo.findByUsername(dbUser.getUsername());

    if(userFromDb.isPresent()) return false;

    dbUser.setRoles(Collections.singleton("USER").toArray(new String[0]));
    dbUser.setActivationCode(UUID.randomUUID().toString());
    dbUser.setPassword(enc.encode(dbUser.getPassword()));

    dbUserRepo.save(dbUser);
    log.info(String.format("save user name = %s, email = %s, activationCode = %s",
        dbUser.getName(), dbUser.getEmail(), dbUser.getActivationCode()));

    if (!dbUser.getEmail().isEmpty()) {
      String message = String.format(
          "Hello, %s! \n " +
              "Welcome to BlitzPost. Please, " +
              "visit next linc http://localhost:8080/activate/%s",
          dbUser.getName(),
          dbUser.getActivationCode()
      );
      log.info(String.format(message));
      mailSender.send(dbUser.getEmail(), "Activation code", message);
      log.info(String.format("mail Send to user name = %s, email = %s ",
          dbUser.getName(), dbUser.getEmail()));
      return true;
    } else {
      log.info("mail not send");
      return false;
    }
  }

  public boolean activateUser(String code) {
    DbUser dbUser = dbUserRepo.findByActivationCode(code);
    if (dbUser == null) return false;
    dbUser.setActivationCode(null);
    dbUserRepo.save(dbUser);
    return true;
  }

}

