package com.portfolio.ReadPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class AdminController {


    HttpSession session;
    @RequestMapping("admin.do")
    public String adminPage() {
        return "admin";
    }
}
