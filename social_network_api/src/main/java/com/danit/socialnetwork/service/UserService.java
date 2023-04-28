package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.DbUser;

import java.util.Optional;

public interface UserService {
  public Optional<DbUser> findByUsername(String username);
  public boolean activateUser(Integer code);
  boolean save (DbUser dbUser);
  boolean sendLetter(String name, String email);

}
