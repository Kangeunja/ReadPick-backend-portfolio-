package com.portfolio.ReadPick;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // 리액트 애플리케이션 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    private final JsonArgumentResolver jsonArgumentResolver;

    public WebConfig(JsonArgumentResolver jsonArgumentResolver) {
        this.jsonArgumentResolver = jsonArgumentResolver;
        // System.out.println("JsonArgumentResolver 등록됨");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {

        // System.out.println("JsonArgumentResolver 등록됨");
        resolvers.add(jsonArgumentResolver);
    }
}
