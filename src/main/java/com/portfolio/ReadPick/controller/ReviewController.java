package com.portfolio.ReadPick.controller;

import javax.security.auth.login.LoginException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.ReviewMapper;
import com.portfolio.ReadPick.vo.ReviewVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;


@RestController
public class ReviewController {

    @Autowired
    HttpSession session;

    @Autowired
    ReviewMapper reviewMapper;

    @GetMapping("reviewInsert")
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
    public ResponseEntity<ReviewVo> modifyReview(int userIdx, int bookIdx) {

        UserVo user = (UserVo) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(reviewMapper.selectOneReview(userIdx, bookIdx));
    }


    // 리뷰수정
    // @GetMapping("reviewUpdate")
    // @Operation(summary = "리뷰수정확인버튼", description = "리뷰를 새로운 내용으로 수정")
    // public ResponseEntity<String> reviewUpdate(@RequestBody ReviewVo reviewVo) {

        
    //     try {
            
    //         reviewMapper.reviewUpdate(reviewVo);
    //     } catch (Exception e) {
    //         System.out.println(e);
    //         return ResponseEntity.ok("reviewUpdate:fail");
    //     }

    //     return ResponseEntity.ok("success");
    // }
    

}
