package com.portfolio.ReadPick.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginCheckInterceptor loginCheckInterceptor;

    // CORS 설정
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://43.200.71.170:3000") // 리액트 애플리케이션 주소
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckInterceptor)
                .addPathPatterns("/myPage/userInfo"); // 로그인 체크할 URL 패턴;
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
            .addResourceHandler("/assets/profile/**", "http://43.200.71.170:8080/ReadPickImages/**") // 이 URL로 요청 오면
            .addResourceLocations("file:/home/ubuntu/ReadPickImages/"); // 이 실제 디렉토리에서 파일 찾음
    }

    @Override
public void addViewControllers(ViewControllerRegistry registry) {
    // /api가 아닌 모든 경로를 index.html로 포워딩
    registry.addViewController("/{path:[^\\.]*}")
            .setViewName("forward:/index.html");
    registry.addViewController("/**/{path:[^\\.]*}")
            .setViewName("forward:/index.html");
}
}

