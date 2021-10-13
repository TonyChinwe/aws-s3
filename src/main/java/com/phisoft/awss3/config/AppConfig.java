package com.phisoft.awss3.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * <p>The configuration that allows cross-origin requests from front-end application to back-end</p>
 */
@Configuration
public class AppConfig implements WebMvcConfigurer {
    /**
     * {@inheritDoc}
     */

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET","POST");
    }

}
