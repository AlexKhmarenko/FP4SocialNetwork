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
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
  private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
  private final JwtAuthFilter jwtFilter;
  private final UserRepository userRepository;
  private final JwtTokenService jwtTokenService;

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Bean
  GuavaCache guavaCache() {
    return new GuavaCache();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    final String[] userName = new String[1];

    http.csrf().disable().headers().frameOptions().disable();

    http.authorizeRequests().antMatchers("/login",
            "/h2/**",
            "/sendLetter",
            "/activate",
            "/checkEmail",
            "/registration",
            "/changepassword*",
            "/newpassword",
            "/home",
            "/",
            "/**").permitAll()
        .antMatchers()
        .authenticated()
        .anyRequest()
        .authenticated();
    http.oauth2Login(l -> l
        .loginPage("/oauth2/google")
        .successHandler((request, response, authentication) -> {
          OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

          userName[0] = oauth2User.getAttributes().get("given_name").toString();
          String userSurname = oauth2User.getAttributes().get("family_name").toString();
          String userMail = oauth2User.getAttributes().get("email").toString();

          log.info("User with username {} has logged in", userName[0]);
          log.info("User name - {}", userName[0]);
          log.info("User surname - {}", userSurname);
          log.info("User e-mail - {}", userMail);
          Optional<DbUser> maybeUser = userRepository.findDbUserByEmail(userMail);
          Integer userId = 0;
          String birthday = "false";

          if (maybeUser.isEmpty()) {
            Transliterator transliteratorUa = Transliterator.getInstance("Ukrainian-Latin/BGN");
            Transliterator transliteratorRu = Transliterator.getInstance("Russian-Latin/BGN");
            String latinName = transliteratorUa.transliterate(userName[0]);
            latinName = transliteratorRu.transliterate(latinName);
            DbUser newUser = new DbUser(latinName,
                UUID.randomUUID().toString(), userMail,
                userName[0], LocalDate.of(1900, 1, 1));
            userRepository.save(newUser);
          } else {
            userId = maybeUser.get().getUserId();
            LocalDate dob = maybeUser.get().getDateOfBirth();
            if (!dob.isEqual(LocalDate.of(1900, 1, 1))) {
              birthday = "true";
            }


          }
          //          response.sendRedirect("/home");
          String token = jwtTokenService.generateToken(userId, true);
          //              boolean dob = true;

          //          String jsonBody = "{\"userToken\": \"" + token + "\",\"dob\": \"" + dob + "\"}";

          //          RestTemplate restTemplate = new RestTemplate();

          //          HttpHeaders headers = new HttpHeaders();
          //          headers.setContentType(MediaType.APPLICATION_JSON);

          log.info("TEST");


          //          HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);
          //          log.info(entity);

          //          String url = "http://localhost:3000/google"; // Ваш URL
          //          String url = "http://localhost:3000/oauth2login"; // Ваш URL
          //          HttpMethod method = HttpMethod.POST;

          //          log.info(restTemplate.exchange(url, method, entity, String.class));

          //          log.info("FALSE");


          response.sendRedirect("http://localhost:3000?token=" + token + "&" + "birthday=" + birthday);
        }).permitAll()

    );
    http.logout(l -> l
        .logoutSuccessHandler((request, response, authentication) -> {
          log.info("User with username {} has logged out", userName[0]);
          //                    response.sendRedirect("/oauth2/authorization/google");
          response.sendRedirect("/login");
        })
        .clearAuthentication(true)
        .invalidateHttpSession(true)
        .deleteCookies("JSESSIONID")
        .deleteCookies("XSRF-TOKEN").permitAll());


    //    http.rememberMe();
    http.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.NEVER);

    http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
    http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
  }

}





