package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfolio.ReadPick.dao.BookMapper;

@Controller
public class MainPageController {

    @Autowired
    BookMapper bookMapper;

    @RequestMapping("mainPage.do")
    public String mainPage(Model model) {

        // mainPage에 출력할 서브카테고리 이름들
        List<String> bsNameList = bookMapper.selectBsNameList();
        model.addAttribute("bsNameList", bsNameList);


        
        return "index";
    }
    
}
