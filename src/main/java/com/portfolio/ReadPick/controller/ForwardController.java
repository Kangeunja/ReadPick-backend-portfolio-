package com.portfolio.ReadPick.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api")
public class ForwardController {

    /**
     * React SPA 경로를 index.html로 포워딩
     * - 정적 리소스(.js, .css, .png 등)와 API(/api/**) 요청은 제외
     */
    @RequestMapping(value = {"/{path:[^\\.]*}", "/**/{path:[^\\.]*}"})
    public String forwardSPA() {
        return "forward:/index.html";
    }
}
