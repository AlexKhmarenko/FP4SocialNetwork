package com.danit.socialnetwork.javatpoint;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.danit.socialnetwork")
public class MvcConfiguration implements WebMvcConfigurer {
  @Override
  public void addResourceHandlers(final ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/styles/css/**")
        .addResourceLocations("classpath:/static/css/");
    registry.addResourceHandler("/images/**")
        .addResourceLocations("classpath:/static/images/");
    registry.addResourceHandler("/js/**")
        .addResourceLocations("classpath:/static/js/");
  }
}