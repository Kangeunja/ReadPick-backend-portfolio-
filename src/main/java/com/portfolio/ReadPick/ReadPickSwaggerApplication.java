package com.portfolio.ReadPick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.v3.oas.annotations.tags.Tag;


@SpringBootApplication
public class ReadPickSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadPickSwaggerApplication.class, args);
    }
}

@Tag(name = "Example Controller", description = "Controller to demonstrate Springdoc OpenAPI")
@Controller
@RequestMapping("/api")
class ExampleController {

    
    
}
