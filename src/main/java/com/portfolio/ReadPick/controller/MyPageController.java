package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.dao.MyPageMapper;
import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class MyPageController {

    @Autowired
    HttpSession session;

    @Autowired
    BookmarkMapper bookmarkMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    BookMapper bookMapper;

    @Autowired
    MyPageMapper myPageMapper;

    // @PostMapping("myPageBookList")
    // public ResponseEntity<BookmarkVo> myPageBookList() {
    // UserVo user = (UserVo) session.getAttribute("user");
    // BookmarkVo bookmarkList =
    // bookmarkMapper.selectMyPageBookList(user.getUserIdx());
    // return ResponseEntity.ok(bookmarkList);
    // }

    @PostMapping("userInfo")
    @Operation(summary = "회원정보", description = "회원정보")
    public ResponseEntity<UserVo> userInfo(HttpServletResponse response) {
        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("userInfoModify")
    @Operation(summary = "회원정보 변경", description = "회원정보 변경")
    public ResponseEntity<String> userInfoModify(@RequestBody UserVo userVo) {
        int userIdx = ((UserVo) session.getAttribute("user")).getUserIdx();
        userVo.setUserIdx(userIdx);
        int res = myPageMapper.userInfoModify(userVo);
        return ResponseEntity.ok("success");
    }

    @PostMapping("userPickBookList")
    @Operation(summary = "유저가 찜한 책 리스트")
    public ResponseEntity<List<BookVo>> userPickBookList() {
        UserVo user = (UserVo) session.getAttribute("user");
        BookmarkVo bookmarkVo = new BookmarkVo();
        bookmarkVo.setUserIdx(user.getUserIdx());
        bookmarkVo.setIsBookmarked("Y");
        List<BookVo> bookList = new ArrayList<>();
        List<BookmarkVo> bookmarkList = myPageMapper.bookmarkList(bookmarkVo);
        if (bookmarkList.size() == 0) {
            return ResponseEntity.ok(bookList);
        }
        ;
        for (int i = 0; i < bookmarkList.size(); i++) {
            bookList.add(bookMapper.selectOneBookByBIdx(bookmarkList.get(i).getBIdx()));
        }
        return ResponseEntity.ok(bookList);
    }

    @PostMapping("bookmarkImageList")
    @Operation(summary = "유저가 찜한 책 사진")
    public ResponseEntity<List<BookImageVo>> bookmarkImageList() {
        UserVo user = (UserVo) session.getAttribute("user");
        BookmarkVo bookmarkVo = new BookmarkVo();
        bookmarkVo.setUserIdx(user.getUserIdx());
        bookmarkVo.setIsBookmarked("Y");
        List<BookmarkVo> bookmarkList = myPageMapper.bookmarkList(bookmarkVo);
        List<BookImageVo> imageList = new ArrayList<>();
        if (bookmarkList.size() == 0) {
            return ResponseEntity.ok(imageList);
        }
        for (int i = 0; i < bookmarkList.size(); i++) {
            imageList.add(myPageMapper.bookmarkImageList(bookmarkList.get(i).getBIdx()));
        }
        return ResponseEntity.ok(imageList);
    }

}
