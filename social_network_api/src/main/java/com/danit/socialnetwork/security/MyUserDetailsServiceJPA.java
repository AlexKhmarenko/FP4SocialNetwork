package com.danit.socialnetwork.security;

import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.DbUserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Configuration
@RequiredArgsConstructor
public class MyUserDetailsServiceJPA implements UserDetailsService {

  private final DbUserRepo dbUserRepo;

  private UserDetails mapper(DbUser dbUser) {
    return User
        .withUsername(dbUser.getUsername())
        .password(dbUser.getPassword())
        .roles("USER")
        .build();
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return dbUserRepo.findByUsername(username)
        .map(this::mapper)
        .orElseThrow(() -> new UsernameNotFoundException(
            String.format("user `%s` not found", username)
        ));
  }

}

