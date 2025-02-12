package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;

public class UserController {

    @Autowired
    HttpSession session;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

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

        return "redirect:../home";
    }

    // 로그아웃
    @RequestMapping("logout.do")
    public String logout() {

        session.removeAttribute("user");

        return "redirect:../home";
    }// end:logout()

    // 아이디 중복체크
    @RequestMapping(value = "check_id.do", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String check_id(String id) {

        UserVo vo = userMapper.selectOneFromId(id);

        boolean bResult = false;
        if (vo == null)
            bResult = true;
        else
            bResult = false;

        String json = String.format("{\"result\": %b }", bResult);

        return json;
    }// end:check_id()

    // 회원가입
    @RequestMapping("userInsert.do")
    public String userInsert(UserVo user) {

        user.setAdminAt("N");
        int res = userMapper.userInsert(user);

        return "redirect:/userPick";
    }

    @RequestMapping("userPick.do")
    public String userPick(Model model) {
        List<BookCategoryVo> bsList = bookCategoryMapper.selectBsList();
        List<BookCategoryVo> bssList = new ArrayList<BookCategoryVo>();
        for (int i = 0; i < bsList.size(); i++) {
            bssList = bookCategoryMapper.selectBssList(bsList.get(i).getBsIdx());
        }
        model.addAttribute("bsList", bsList);
        model.addAttribute("bssList", bssList);
        return "userPick";
    }

    @RequestMapping("userPickResult.do")
    public String userPickResult(Model model) {
        // 아직미완성@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
        return "userPickResult";
    }
}
