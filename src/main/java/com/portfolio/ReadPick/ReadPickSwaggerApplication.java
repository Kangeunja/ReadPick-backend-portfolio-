package com.portfolio.ReadPick;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookVo;

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

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    // mainPage에 출력할 서브카테고리들
    @GetMapping("bsList")
    public List<BookCategoryVo> bsNameList() {

        return bookCategoryMapper.selectBsList();
    }

    // bsIdx로 책 리스트를 조회
    @GetMapping("bookListByBsIdx")
    public List<BookVo> requestMethodName(int bsIdx) {

        return bookMapper.selectBookListByBsName(bsIdx);
    }

    @GetMapping("bookOneByIsbn") // isbn : 프론트에서 보내줄 것
    public BookVo requestMethodName(String isbn) {

        return bookMapper.selectOneBookByIsbn(isbn);
    }



}


