package com.portfolio.ReadPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainPageController {

    @RequestMapping("mainPage.do")
    public String mainPage() {
        
        return "index";
    }
    
}
