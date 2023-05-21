package com.danit.socialnetwork.security;

import com.danit.socialnetwork.config.GuavaCache;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.UserRepository;
import com.ibm.icu.text.Transliterator;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
Expand All
	@@ -15,6 +20,8 @@
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;
import java.util.Optional;
Expand All
	@@ -40,10 +47,16 @@ GuavaCache guavaCache() {
    return new GuavaCache();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    final String[] userName = new String[1];

    http.csrf().disable().headers().frameOptions().disable();

Expand Down
Expand Up
	@@ -87,7 +100,7 @@ protected void configure(HttpSecurity http) throws Exception {
                userName[0], LocalDate.of(1900, 1, 1));
            userRepository.save(newUser);
          }
          response.sendRedirect("/home");
        }).permitAll()
    );
    http.logout(l -> l
Expand All
	@@ -105,9 +118,11 @@ protected void configure(HttpSecurity http) throws Exception {
    http.rememberMe();

    http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    //    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    //
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

}
