package com.rem.cs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        final String[] resourceLocation = {"file:images/", "file:c:/images/"};
        registry.addResourceHandler("/images/**").addResourceLocations(resourceLocation);
    }
}
