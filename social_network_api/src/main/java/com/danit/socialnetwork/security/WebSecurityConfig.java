package com.danit.socialnetwork.security;

import com.danit.socialnetwork.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtAuthFilter jwtFilter;

  @Bean
  public BCryptPasswordEncoder getPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.
        csrf().disable()
        .headers().frameOptions().disable();

    http.
        authorizeRequests()
        .antMatchers(
            "/h2/**", "/img**", "/js**", "/css**",
            "/registration", "/").permitAll() // in production delete page "/users"

        .anyRequest().authenticated();

    http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .defaultSuccessUrl("/users")
        .permitAll();

    http.rememberMe();

    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/"));
  }
}
