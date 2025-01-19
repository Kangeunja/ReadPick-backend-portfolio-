package com.portfolio.ReadPick;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2  // Swagger 2 활성화
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)  // Swagger 2로 설정
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.portfolio.ReadPick")) // Swagger 문서화할 기본 패키지 지정
                .paths(PathSelectors.any())  // 모든 경로를 문서화
                .build()
                .apiInfo(new ApiInfoBuilder()
                        .title("ReadPick API Documentation")
                        .description("API Documentation for the ReadPick Application")
                        .version("1.0")
                        .build());
    }
}
