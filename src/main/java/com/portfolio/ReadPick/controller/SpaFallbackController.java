// package com.portfolio.ReadPick.controller;

// import org.springframework.boot.web.servlet.error.ErrorController;
// import org.springframework.stereotype.Controller;
// import org.springframework.web.bind.annotation.RequestMapping;

// import jakarta.servlet.http.HttpServletRequest;

// @Controller
// public class SpaFallbackController implements ErrorController {

//     private static final String ERROR_PATH = "/error";

//     @RequestMapping(ERROR_PATH)
//     public String handleError(HttpServletRequest request) {
//         Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");

        

//         // 404일 때만 SPA index.html로 포워딩
//         if (statusCode != null && statusCode == 404) {
//             return "forward:/index.html";
//         }

//         // 나머지는 기본 error 페이지
//         if (statusCode == 404) {
//             System.out.println(statusCode);
//             return "error.html";
//         }
//         return "error.html";
//     }

//     public String getErrorPath() {
//         return ERROR_PATH;
//     }
// }
