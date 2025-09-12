package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.service.NaverSearchIsbnService;
import com.portfolio.ReadPick.vo.BookVo;

@RestController
@RequestMapping("api")
public class DBController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    NaverSearchIsbnService searchIsbn;

    @GetMapping("DBDataInsert")
    public String DBData() {

        List<BookVo> searchList = bookMapper.selectSearchList();
        for (int i = 0; i < searchList.size(); i++) {
            String searchOneName = searchList.get(i).getKeywordName();
            searchIsbn.searchIsbnSave(searchOneName);
        }
        System.out.println("=====책 저장 끝=====");
        return "책 저장 끝";
    }
}
