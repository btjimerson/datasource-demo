package com.yugabyte.datasource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuration to register the UserInterceptor.
 */
@Component
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    UserInterceptor userInterceptor;

    /**
     * Adds the {@link com.yugabyte.datasource.UserInterceptor} to the
     * {@link org.springframework.web.servlet.config.annotation.InterceptorRegistry}.
     * 
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurer#addInterceptors(InterceptorRegistry)
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userInterceptor);
        WebMvcConfigurer.super.addInterceptors(registry);
    }

}
