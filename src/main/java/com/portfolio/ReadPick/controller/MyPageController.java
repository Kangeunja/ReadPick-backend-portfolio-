package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.dao.MyPageMapper;
import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserPickDTO;
import com.portfolio.ReadPick.vo.UserSessionDTO;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RequestMapping("api/myPage")
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
    public ResponseEntity<UserSessionDTO> userInfo(HttpServletResponse response) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            Cookie cookie = new Cookie("JSESSIONID", null);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
        user.setPw(userMapper.selectPwFromUserIdx(user.getUserIdx()));
        return ResponseEntity.ok(user);
    }

    @PostMapping("userInfoModify")
    @Operation(summary = "회원정보 변경", description = "회원정보 변경 보내줄 데이터는 유저이름, 닉네임, 아이디, 비밀번호, 이메일")
    public ResponseEntity<String> userInfoModify(@RequestBody UserVo userVo) {
        System.out.println(userVo);
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("fail:userNull");
        }
        int userIdx = user.getUserIdx();
        userVo.setUserIdx(userIdx);
        int res = myPageMapper.userInfoModify(userVo);
        boolean isSuccess = res > 0;
        if (!isSuccess) {
            return ResponseEntity.ok("fail:modifyFail");
        }else{
            user.setEmail(userVo.getEmail());
            user.setNickName(userVo.getNickName());
            user.setUserName(userVo.getUserName());
            user.setId(userVo.getId());
            user.setPw(userVo.getPw());
            session.setAttribute("user", user);
            return ResponseEntity.ok("success");
        }
    }

    @PostMapping("userPickBookList")
    @Operation(summary = "유저가 찜한 책 리스트")
    public ResponseEntity<List<BookVo>> userPickBookList() {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok(null);
        }
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
            bookList.add(bookMapper.selectOneBookByBookIdx(bookmarkList.get(i).getBookIdx()));
        }
        return ResponseEntity.ok(bookList);
    }

    @PostMapping("bookmarkImageList")
    @Operation(summary = "유저가 찜한 책 사진")
    public ResponseEntity<List<BookImageVo>> bookmarkImageList() {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok(null);
        }
        BookmarkVo bookmarkVo = new BookmarkVo();
        bookmarkVo.setUserIdx(user.getUserIdx());
        bookmarkVo.setIsBookmarked("Y");
        List<BookmarkVo> bookmarkList = myPageMapper.bookmarkList(bookmarkVo);
        List<BookImageVo> imageList = new ArrayList<>();
        if (bookmarkList.size() == 0) {
            return ResponseEntity.ok(imageList);
        }
        for (int i = 0; i < bookmarkList.size(); i++) {
            imageList.add(myPageMapper.bookmarkImageList(bookmarkList.get(i).getBookIdx()));
        }
        return ResponseEntity.ok(imageList);
    }

    @PostMapping("myPickGenre")
    @Operation(summary = "유저가 찜한 장르의 리스트")
    public ResponseEntity<List<UserPickDTO>> myPickGenre() {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok(null);
        }

        List<UserPickDTO> genreList = myPageMapper.myPickGenre(user.getUserIdx());
        
        return ResponseEntity.ok(genreList);
    }

}
