package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.AdminMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.AdminReviewVo;
import com.portfolio.ReadPick.vo.AdminUserVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.UserSessionDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api")
public class AdminController {

    @Autowired
    HttpSession session;

    @Autowired
    AdminMapper adminMapper;

    @Autowired
    BookMapper bookMapper;

    @GetMapping("adminUserList")
    @Operation(summary = "유저 목록 조회", description = "관리자 권한으로 유저 목록을 조회합니다.")
    public ResponseEntity<List<AdminUserVo>> adminUserList() {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        List<AdminUserVo> userList = adminMapper.userList();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("adminUserDelete")
    @Operation(summary = "유저 삭제", description = "관리자 권한으로 유저를 삭제합니다.")
    public ResponseEntity<String> adminUserDelete(int userIdx) {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        try {
            int res = adminMapper.userDelete(userIdx);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("userDelete:fail");
        }
        return ResponseEntity.ok("userDelete:success");
    }

    @GetMapping("adminReviewList")
    @Operation(summary = "리뷰 목록 조회", description = "관리자 권한으로 신고된 리뷰 목록을 조회합니다.")
    public ResponseEntity<List<AdminReviewVo>> adminReviewList() {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        List<Integer> rvIdxList = adminMapper.selectAdminReviewIdxList();
        if (rvIdxList.isEmpty()) {
            return ResponseEntity.ok(null);
        }
        List<AdminReviewVo> reviewList = adminMapper.selectAdminReviewFromIdxList(rvIdxList);
        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("adminReportCount")
    @Operation(summary = "신고 횟수 조회", description = "관리자 권한으로 신고된 리뷰의 총 횟수를 조회합니다.")
    public ResponseEntity<List<Integer>> adminReportCount() {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        List<Integer> reportCountList = adminMapper.selectReportCount();
        return ResponseEntity.ok(reportCountList);
    }

    @GetMapping("adminReviewDelete")
    @Operation(summary = "리뷰 삭제", description = "관리자 권한으로 리뷰를 삭제합니다.")
    public ResponseEntity<String> adminReviewDelete(int rvIdx) {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        try {
            int res = adminMapper.reviewDelete(rvIdx);
            return ResponseEntity.ok("reviewDelete:success");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reviewDelete:fail");
        }
    }

    @GetMapping("adminReviewReset")
    @Operation(summary = "리뷰 신고 초기화", description = "리뷰 신고횟수를 초기화합니다.")
    public ResponseEntity<String> adminReviewReset(int rvIdx) {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        try {
            int res = adminMapper.reviewReset(rvIdx);
            return ResponseEntity.ok("reviewReset:success");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reviewReset:fail");
        }
    }

    @GetMapping("adminBookList")
    @Operation(summary = "책 목록 조회", description = "책 목록을 조회합니다.")
    public ResponseEntity<List<BookVo>> adminBookList() {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        List<BookVo> bookList = adminMapper.selectAdminBookList();
        return ResponseEntity.ok(bookList);
    }

    // @GetMapping("adminBookInsert")
    // public ResponseEntity<List<BookVo>> adminBookInsert() {
    // UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
    // if (userSession == null || userSession.getUserIdx() != 1) {
    // return ResponseEntity.status(403).build();
    // }
    // List<BookVo> bookList = adminMapper.selectAdminBookList();
    // return ResponseEntity.ok(bookList);
    // }

    // @GetMapping("adminBookUpdate")
    // public ResponseEntity<List<BookVo>> adminBookUpdate() {
    // UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
    // if (userSession == null || userSession.getUserIdx() != 1) {
    // return ResponseEntity.status(403).build();
    // }
    // List<BookVo> bookList = adminMapper.selectAdminBookList();
    // return ResponseEntity.ok(bookList);
    // }

    @GetMapping("adminBookDelete")
    @Operation(summary = "책 삭제", description = "관리자 권한으로 책을 삭제합니다.")
    public ResponseEntity<String> adminBookDelete(int bookIdx) {
        UserSessionDTO userSession = (UserSessionDTO) session.getAttribute("user");
        if (userSession == null || userSession.getUserIdx() != 1) {
            return ResponseEntity.status(403).build();
        }
        int res = adminMapper.deleteBook(bookIdx);
        if (res == 0) {
            return ResponseEntity.ok("bookDelete:fail");
        }
        return ResponseEntity.ok("bookDelete:success");
    }
    // 유저 조회 및 삭제 기능 추가

}
