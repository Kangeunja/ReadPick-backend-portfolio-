package com.portfolio.ReadPick;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookMapper;

import io.swagger.v3.oas.annotations.tags.Tag;


@SpringBootApplication
public class ReadPickSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadPickSwaggerApplication.class, args);
    }
}

@Tag(name = "readPick-api", description = "Controller to demonstrate Springdoc OpenAPI")
@RestController
@RequestMapping("/api")
class ExampleController {

    @Autowired
    BookMapper bookMapper;

    @GetMapping("bsNameList")
    public List<String> bsNameList() {

        // mainPage에 출력할 메인카테고리 이름들
        List<String> bsNameList = bookMapper.selectBsNameList();

        return bsNameList;
    }

}


