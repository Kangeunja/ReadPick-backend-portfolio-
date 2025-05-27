package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.AdminMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.AdminReviewVo;
import com.portfolio.ReadPick.vo.AdminUserVo;
import com.portfolio.ReadPick.vo.BookVo;

import jakarta.servlet.http.HttpSession;

@RestController
public class AdminController {

    @Autowired
    HttpSession session;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    BookMapper bookMapper;

    @GetMapping("adminUserList")
    public ResponseEntity<List<AdminUserVo>> adminUserList() {
        List<AdminUserVo> userList = adminMapper.userList();    
        return ResponseEntity.ok(userList);
    }

    @GetMapping("adminReviewList")
    public ResponseEntity<List<AdminReviewVo>> adminReviewList() {
        List<Integer> rvIdxList = adminMapper.selectAdminReviewIdxList();
        List<AdminReviewVo> reviewList = adminMapper.selectAdminReviewFromIdxList(rvIdxList);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("adminBookList")
    public ResponseEntity<List<BookVo>> adminBookList() {
        List<BookVo> bookList = adminMapper.selectAdminBookList();
        return ResponseEntity.ok(bookList);
    }
    // 리뷰삭제기능 추가
    // 책 수정 삭제 기능 추가
    // 유저 조회 및 삭제 기능 추가

}
