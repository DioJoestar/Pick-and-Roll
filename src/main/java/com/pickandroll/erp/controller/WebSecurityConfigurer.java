package com.pickandroll.erp.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebSecurityConfigurer implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registre) {
        registre.addViewController("/").setViewName("index");
        registre.addViewController("/login");
        registre.addViewController("/error/403").setViewName("/error/403");
    }
}
