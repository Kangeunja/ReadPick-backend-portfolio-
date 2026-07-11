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
        System.out.println("검색할 키워드 개수: " + searchList.size());
        for (int i = 0; i < searchList.size(); i++) {
            String searchOneName = searchList.get(i).getKeywordName();
            System.out.println("현재 검색 중인 키워드: " + searchOneName);
            searchIsbn.searchIsbnSave(searchOneName);
        }
        System.out.println("=====책 저장 끝=====");
        return "책 저장 끝";
    }
}
