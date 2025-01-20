package com.portfolio.ReadPick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    // @GetMapping("/hello")
    // @ResponseBody
    // public String hello() {
    //     return "감자!";
    // }
    
    
    // @PostMapping("/post")
    // @ResponseBody
    // public String postMethodName(@RequestBody String entity) {

    //     entity = "hello";
    //     return entity;
    // }

    
    
}
