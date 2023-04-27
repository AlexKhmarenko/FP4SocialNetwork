package com.danit.socialnetwork.service;

import com.danit.socialnetwork.model.DbUser;

import java.util.List;
import java.util.Optional;

public interface UserService {
  public List findAll();
  public Optional<DbUser> findByUsername(String username);
  public Optional<DbUser> findByUsernameAndPassword(String username, String password);
  public boolean saveUser(DbUser dbUser);
  public boolean activateUser(String code);

}
