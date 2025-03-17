package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.ReviewMapper;
import com.portfolio.ReadPick.vo.ReviewUserVo;
import com.portfolio.ReadPick.vo.ReviewVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
public class ReviewController {

    @Autowired
    HttpSession session;

    @Autowired
    ReviewMapper reviewMapper;

    @PostMapping("reviewInsert")
    @Operation(summary = "리뷰작성", description = "리뷰작성 유저가 입력할 부분은 내용밖에 없음 <br> 프론트에서 bookIdx를 보내줄것")
    public ResponseEntity<String> reviewInsert(@RequestBody ReviewVo reviewVo) {
        
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }
        try {
            reviewVo.setUserIdx(user.getUserIdx());
            reviewVo.setReviewAt("Y");
            reviewMapper.insertReview(reviewVo);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reviewInsert:fail");
        }

        return ResponseEntity.ok("success");
    }

    // 수정을 위한 기존리뷰정보전달
    @GetMapping("modifyReview")
    @Operation(summary = "기존리뷰정보전달", description = "리뷰수정버튼을 누르면 해당 리뷰의 정보를 전달")
    public ResponseEntity<ReviewVo> modifyReview(int bookIdx) {

        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            System.out.println("login:fail");
            return ResponseEntity.ok(null);
        }
        int userIdx = user.getUserIdx();

        ReviewVo review = new ReviewVo();

        try {
            review = reviewMapper.selectOneReview(userIdx, bookIdx);
            if (review == null) {
                System.out.println("review가 비어있음");
                return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("modifyReview:fail");
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(review);
    }

    // 리뷰수정
    @PostMapping("reviewUpdate")
    @Operation(summary = "리뷰수정확인버튼", description = "리뷰를 새로운 내용으로 수정")
    public ResponseEntity<String> reviewUpdate(@RequestBody ReviewVo reviewVo) {

        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }

        try {
            reviewMapper.reviewUpdate(reviewVo);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reviewUpdate:fail");
        }

        return ResponseEntity.ok("success");
    }

    // 리뷰삭제
    @GetMapping("reviewDelete")
    @Operation(summary = "리뷰삭제", description = "리뷰삭제")
    public ResponseEntity<String> reviewDelete(int bookIdx) {
        UserVo user = (UserVo) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }

        int userIdx = user.getUserIdx();
        try {
            reviewMapper.reviewDelete(userIdx, bookIdx);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reviewDelete:fail");
        }

        return ResponseEntity.ok("success");
    }

    @GetMapping("reviewList")
    @Operation(summary = "리뷰리스트", description = "프론트에서 bookIdx를 보내줄 것")
    public ResponseEntity<List<ReviewUserVo>> reviewList(int bookIdx) {

        List<ReviewUserVo> review = new ArrayList<>();

        try {
            review = reviewMapper.selectReview(bookIdx);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(review);
    }

    // 무한 스크롤 페이징
    @GetMapping("reviewMore")
    @Operation(summary = "리뷰 무한 스크롤", description = "프론트에서 마지막으로 조회된 리뷰의 rvIdx를 보내줄 것")
    public ResponseEntity<List<ReviewUserVo>> reviewMore(int rvIdx) {

        List<ReviewUserVo> review = new ArrayList<>();

        try {
            int bookIdx = reviewMapper.selectOneBookIdx(rvIdx); 
            review = reviewMapper.selectReviewMore(bookIdx, rvIdx);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("페이징실패");
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(review);
    }

}
