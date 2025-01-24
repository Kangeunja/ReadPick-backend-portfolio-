package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfolio.ReadPick.dao.BookMapper;
// import com.portfolio.ReadPick.service.NaverSearchService;
import com.portfolio.ReadPick.vo.BookVo;

@Controller
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    // NaverSearchService searchService;

    @RequestMapping("DBData.do")
    public String DBData() {

        List<BookVo> searchList = bookMapper.selectSearchList();
        for (int i = 0; i < searchList.size(); i++) {
            String searchOneName = searchList.get(i).getKeywordName();
            // searchService.searchAndSave(searchOneName);
            System.out.println(searchOneName);
        }
        return "home";
    }

}
