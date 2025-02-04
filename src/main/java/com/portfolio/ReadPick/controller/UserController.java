package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.UserVo;

import jakarta.servlet.http.HttpSession;

public class UserController {

    @Autowired
    HttpSession session;
    

    @Autowired
    UserMapper userMapper;



    @RequestMapping("list.do")
    public String list(Model model) {

        List<UserVo> list = userMapper.selectList();

        model.addAttribute("list", list);

        return "home";
    }

    @RequestMapping("login.do")
    public String login(String id, String password, String url, RedirectAttributes ra, Model model) {

        UserVo user = userMapper.selectOneFromId(id);

        // 아이디가 없는(틀린)경우
        if (user == null) {
            ra.addAttribute("reason", "fail_id");
            return "redirect:" + url;
        }

        // 비밀번호가 틀린경우
        if (user.getPassword().equals(password) == false) {
            ra.addAttribute("reason", "fail_password");
            return "redirect:" + url;
        }

        // 로그인처리: 현재 로그인된 객체(user)정보를 session저장
        session.setAttribute("user", user);
        model.addAttribute("showSignUpModal", false);

        if (url != null)
            return "redirect:" + url;

        return "redirect:../home.do";
    }

    // 로그아웃
    @RequestMapping("logout.do")
    public String logout() {

        session.removeAttribute("user");

        return "redirect:../home.do";
    }// end:logout()

    // 회원가입
    @RequestMapping("userInsert.do")
    public String userInsert(UserVo user) {

        user.setAdminAt("N");
        int res = userMapper.userInsert(user);

        return "redirect:../home";
    }
}
