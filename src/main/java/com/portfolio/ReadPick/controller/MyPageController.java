package com.portfolio.ReadPick.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

import jakarta.servlet.http.HttpSession;

@RestController
public class MyPageController {

    @Autowired
    HttpSession session;

    @Autowired
    BookmarkMapper bookmarkMapper;

    @Autowired
    UserMapper userMapper;

    @PostMapping("myPageBookList")
    public ResponseEntity<BookmarkVo> myPageBookList() {

        UserVo user = (UserVo) session.getAttribute("user");

        BookmarkVo bookmarkList = bookmarkMapper.selectMyPageBookList(user.getUserIdx());

        return ResponseEntity.ok(bookmarkList);
    }

    @PostMapping("userInfo")
    public ResponseEntity<UserVo> userInfo() {
        UserVo user = (UserVo) session.getAttribute("user");
        return ResponseEntity.ok(user);
    }

    @PostMapping("userInfoModify")
    public ResponseEntity<String> userInfoModify(UserVo user) {

        int res = userMapper.userInfoModify(user);
        return ResponseEntity.ok("success");
    }
}
