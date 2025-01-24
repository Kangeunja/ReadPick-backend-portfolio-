package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.service.NaverSearchIsbnService;
import com.portfolio.ReadPick.vo.BookVo;

@Controller
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    NaverSearchIsbnService searchIsbn;

    @RequestMapping("DBData.do")
    public String DBData() {

        List<BookVo> searchList = bookMapper.selectSearchList();
        for (int i = 0; i < searchList.size(); i++) {
            String searchOneName = searchList.get(i).getKeywordName();
            searchIsbn.searchIsbnSave(searchOneName);
            // System.out.println(searchOneName);
        }
        System.out.println("=====책 저장 끝=====");
        return "home";
    }

}
