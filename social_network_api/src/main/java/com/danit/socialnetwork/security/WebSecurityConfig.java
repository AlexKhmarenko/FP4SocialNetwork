package com.danit.socialnetwork.security;

import com.danit.socialnetwork.config.GuavaCache;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Log4j2
@Order(101)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private final AuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAuthFilter jwtFilter;

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
        //        http.csrf().disable().headers().frameOptions().disable();

        http
                .authorizeRequests(a -> a
                        .antMatchers("/index",
                                "/login",
                                "/out",
                                "/h2/**",
                                "/sendLetter",
                                "/activate",
                                "/checkUsername",
                                "/registration").permitAll()
//                        .antMatchers("/home").authenticated()
                        .anyRequest()
                        .authenticated())
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .csrf(c -> c
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .logout(l -> l
                        .logoutSuccessUrl("/")
//                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();
                            log.info("User with username {} has logged out", oauth2User.getAttributes().get("name"));
                            response.sendRedirect("/oauth2/authorization/google");
                        })
                        .clearAuthentication(true)
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .deleteCookies("XSRF-TOKEN")
                        .permitAll()
                )
                .oauth2Login(l -> l
//                        .loginPage("/log")
                        .successHandler((request, response, authentication) -> {
                            OAuth2User oauth2User = (OAuth2User) authentication.getPrincipal();

                            String userName = oauth2User.getAttributes().get("given_name").toString();
                            String userSurname = oauth2User.getAttributes().get("family_name").toString();
                            String userMail = oauth2User.getAttributes().get("email").toString();

                            log.info("User with username {} has logged in", oauth2User.getAttributes().get("name"));
                            log.info("User name - {}", userName);
                            log.info("User surname - {}", userSurname);
                            log.info("User e-mail - {}", userMail);

                            response.sendRedirect("/out" /*+ userMail*/);
                        }));

        http.rememberMe();

        http.exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint);
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        http.logout().logoutRequestMatcher(new AntPathRequestMatcher("/"));
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
