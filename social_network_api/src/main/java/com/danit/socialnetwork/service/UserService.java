package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.DbUser;

import java.util.Optional;

import java.io.IOException;

public interface UserService {

  public Optional<DbUser> findByUsername(String username);


  DbUser findByUsername(String username);

  public boolean activateUser(Integer code);


  boolean save (DbUser dbUser);


  boolean sendLetter(String name, String email);

  byte[] getProfileImage(String username) throws IOException;

  byte[] getBackgroundImage(String username) throws IOException;

}
