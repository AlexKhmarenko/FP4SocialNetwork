package com.danit.socialnetwork.security;

import com.danit.socialnetwork.JwtAuthFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtUserDetailsService jwtUserDetailsService;
  private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
//  private final JwtAuthFilter jwtFilter;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

//  @Bean
//  MyDelegatedEncoder passwordEncoder() {
//    return new MyDelegatedEncoder();
//  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.
        csrf().disable()
        .headers().frameOptions().disable();

    http.
        authorizeRequests().antMatchers("/login").permitAll();

    http.
        authorizeRequests()
        .antMatchers(
            "/activate/*", "/h2/**",
            "/checkUsername", "/registration", "/").permitAll()
        .antMatchers("/api", "/home").authenticated()
        .anyRequest().authenticated();

    http.formLogin()
        .loginPage("/login")
        .loginProcessingUrl("/login")
        .usernameParameter("username")
        .passwordParameter("password")
        .defaultSuccessUrl("/home", true)
        .permitAll();

    http.rememberMe();


    http.
        exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    http.
        sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

//    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    http.logout()
        .logoutRequestMatcher(new AntPathRequestMatcher("/"));
  }
}
