package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;


@Controller
public class MainPageController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @RequestMapping("mainPage.do")
    public String mainPage(Model model) {

        // mainPage에 출력할 서브카테고리 이름들
        List<BookCategoryVo> bsList = bookCategoryMapper.selectBsList();
        model.addAttribute("bsList", bsList);

        return "index";
    }
    
}
