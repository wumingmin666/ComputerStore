package com.example.computerstore.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @param
 * @return
 */
@Configuration
public class AddPictureConfiguer implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:D:\\Preject\\IDEA\\ComputerStore\\src\\main\\resources\\static\\upload\\");
    }
}
