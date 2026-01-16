package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.ReviewMapper;
import com.portfolio.ReadPick.service.BookService;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.ReviewUserVo;
import com.portfolio.ReadPick.vo.ReviewVo;
import com.portfolio.ReadPick.vo.UserSessionDTO;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("api")
public class ReviewController {

    @Autowired
    HttpSession session;

    @Autowired
    ReviewMapper reviewMapper;

    @Autowired
    BookService bookService;

    @PostMapping("reviewInsert")
    @Operation(summary = "리뷰작성", description = "리뷰작성 유저가 입력할 부분은 내용밖에 없음 <br> 프론트에서 bookIdx를 보내줄것")
    public ResponseEntity<String> reviewInsert(@RequestBody ReviewVo reviewVo) {

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");

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

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
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

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }

        try {
            int userIdx = user.getUserIdx();
            reviewVo.setUserIdx(userIdx);
            reviewMapper.reviewUpdate(reviewVo);
            return ResponseEntity.ok("success");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reviewUpdate:fail");
        }
    }

    // 리뷰삭제
    @GetMapping("reviewDelete")
    @Operation(summary = "리뷰삭제", description = "리뷰삭제")
    public ResponseEntity<String> reviewDelete(int bookIdx) {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");

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

        List<ReviewUserVo> reviewList = new ArrayList<>();

        try {
            reviewList = reviewMapper.selectReview(bookIdx);
            for (ReviewUserVo review : reviewList) {
                // 리뷰 작성자의 프로필 이미지가 없을 경우 기본 이미지로 설정
                if (review.getFileName() == null || review.getFileName().isEmpty()) {
                    review.setFileName("default");
                } else {
                    review.setFileName("http://localhost:8080/ReadPickImages/" + review.getFileName());
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(null);
        }
        System.out.println(reviewList);

        return ResponseEntity.ok(reviewList);
    }

    // 무한 스크롤 페이징
    @GetMapping("reviewMore")
    @Operation(summary = "리뷰 무한 스크롤", description = "프론트에서 마지막으로 조회된 리뷰의 rvIdx를 보내줄 것")
    public ResponseEntity<List<ReviewUserVo>> reviewMore(int rvIdx) {

        List<ReviewUserVo> reviewList = new ArrayList<>();

        try {
            int bookIdx = reviewMapper.selectBookByRvIdx(rvIdx).getBookIdx();
            reviewList = reviewMapper.selectReviewMore(bookIdx, rvIdx);
            for (ReviewUserVo review : reviewList) {
                // 리뷰 작성자의 프로필 이미지가 없을 경우 기본 이미지로 설정
                if (review.getFileName() == null || review.getFileName().isEmpty()) {
                    review.setFileName("default");
                } else {
                    review.setFileName("http://localhost:8080/ReadPickImages/" + review.getFileName());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("페이징실패");
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("reportReview")
    @Operation(summary = "리뷰신고", description = "리뷰신고처리 API")
    public ResponseEntity<String> reportReview(int rvIdx) {

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok("login:fail");
        }
        int userIdx = user.getUserIdx();
        try {
            int res = reviewMapper.insertReportReview(rvIdx, userIdx);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok("reportReview:fail");
        }

        return ResponseEntity.ok("reportReview:success");
    }

    @GetMapping("reviewCount")
    @Operation(summary = "리뷰개수", description = "해당 도서의 리뷰개수 반환")
    public ResponseEntity<Integer> reviewCount(int bookIdx) {
        int count = 0;
        try {
            count = reviewMapper.reviewCount(bookIdx);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(0);
        }
        return ResponseEntity.ok(count);
    }

    // 내리뷰확인

    @GetMapping("myReview")
    @Operation(summary = "내리뷰확인", description = "내가 작성한 리뷰 확인")
    public ResponseEntity<List<ReviewUserVo>> myReview() {

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            System.out.println("login:fail");
            return ResponseEntity.ok(null);
        }

        int userIdx = user.getUserIdx();

        List<ReviewUserVo> review = new ArrayList<>();

        try {
            review = reviewMapper.selectMyReview(userIdx);
            if (review == null) {
                System.out.println("review가 비어있음");
                return ResponseEntity.ok(null);
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("myReview:fail");
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(review);
    }

    @GetMapping("myReviewCount")
    @Operation(summary = "내리뷰개수", description = "내가 작성한 리뷰 개수 확인")
    public ResponseEntity<Integer> myReviewCount() {

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            System.out.println("login:fail");
            return ResponseEntity.ok(0);
        }

        int userIdx = user.getUserIdx();

        int count = 0;

        try {

            count = reviewMapper.selectMyReviewCount(userIdx);
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("myReviewCount:fail");
            return ResponseEntity.ok(0);
        }

        return ResponseEntity.ok(count);
    }

    @GetMapping("myReviewMore")
    @Operation(summary = "내가 쓴 리뷰 무한 스크롤", description = "프론트에서 마지막으로 조회된 리뷰의 rvIdx를 보내줄 것")
    public ResponseEntity<List<ReviewUserVo>> myReviewMore(int rvIdx) {

        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            System.out.println("login:fail");
            return ResponseEntity.ok(null);
        }

        int userIdx = user.getUserIdx();

        List<ReviewUserVo> reviewList = new ArrayList<>();

        try {
            reviewList = reviewMapper.selectMyReviewMore(userIdx, rvIdx);
            for (ReviewUserVo review : reviewList) {
                // 리뷰 작성자의 프로필 이미지가 없을 경우 기본 이미지로 설정
                if (review.getFileName() == null || review.getFileName().isEmpty()) {
                    review.setFileName("default");
                } else {
                    review.setFileName("http://localhost:8080/ReadPickImages/" + review.getFileName());
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("페이징실패");
            return ResponseEntity.ok(null);
        }

        return ResponseEntity.ok(reviewList);
    }

    @GetMapping("userReviewBook")
    @Operation(summary = "유저가 쓴 리뷰의 책 정보", description = "유저가 쓴 리뷰의 책정보를 불러오기")
    public ResponseEntity<List<BookVo>> userReviewBook() {
        UserSessionDTO user = (UserSessionDTO) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.ok(null);
        }
        BookImageVo image = new BookImageVo();
        BookVo book = new BookVo();
        List<Integer> myRvIdxs = new ArrayList<>();
        for (ReviewUserVo rv : reviewMapper.selectMyReview(user.getUserIdx()))
            myRvIdxs.add(rv.getRvIdx());
        List<BookVo> reviewBooks = new ArrayList<>();
        for (int rvIdx : myRvIdxs){
            book = reviewMapper.selectBookByRvIdx(rvIdx);
            image = bookService.bookImageService(book.getBookIdx());
            book.setBookImageName(image.getFileName());
            reviewBooks.add(book);
        }
        return ResponseEntity.ok(reviewBooks);
    }

}
