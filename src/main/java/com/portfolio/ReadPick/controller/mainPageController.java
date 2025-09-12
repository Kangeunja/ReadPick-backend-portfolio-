package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;


@RestController
@RequestMapping("api")
public class MainPageController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @GetMapping("mainPageBsList")
    public ResponseEntity<List<BookCategoryVo>> mainPage() {

        return ResponseEntity.ok(bookCategoryMapper.selectBsList());
    }
    
}
