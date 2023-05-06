package com.danit.socialnetwork.security;

import com.danit.socialnetwork.config.GuavaCache;
import com.danit.socialnetwork.model.DbUser;
import com.danit.socialnetwork.repository.UserRepository;
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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthFilter jwtFilter;
    private final UserRepository userRepository;

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

        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/h2/**",
                        "/sendLetter",
                        "/activate",
                        "/checkUsername",
                        "/registration",
                        "/changepassword*",
                        "/newpassword",
                        "/").permitAll()
                .antMatchers("/home")
                .authenticated()
                .anyRequest()
                .authenticated();
        http.oauth2Login(l -> l
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
                    if (maybeUser.isEmpty()) {
                        DbUser newUser = new DbUser(StringUtils.stripAccents(userName[0]),
                                UUID.randomUUID().toString(), userMail,
                                userName[0], LocalDate.of(1970, 1, 1));
                        userRepository.save(newUser);
                    }
                    response.sendRedirect("/home");
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


        http.rememberMe();

        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

//        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/"));
    }

    // CORS filter
//  @Bean
//  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    return http.cors(Customizer.withDefaults()) // by default uses a Bean by the name of corsConfigurationSource
//        .authorizeRequests(auth -> auth.anyRequest().authenticated()).httpBasic(Customizer.withDefaults()).build();
//  }

    // CORS configuration
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT"));
        configuration.setAllowedHeaders(List.of("Authorization"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
