package com.portfolio.ReadPick.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.service.BookService;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;

@RestController
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookImageMapper bookImageMapper;

    @Autowired
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    BookmarkMapper bookmarkMapper;

    @Autowired
    HttpSession session;

    @Autowired
    BookService bookService;

    // bsName으로 책 리스트를 찾아와 bsCategory에 출력

    @GetMapping("bsListOneByBsIdx")
    @Operation(summary = "선택한 중분류", description = "호출 시 메인페이지에서 선택한 중분류의 bsIdx를 보내줄 것")
    public ResponseEntity<BookCategoryVo> bsListOneByBsIdx(int bsIdx) {
        BookCategoryVo bsNameByBsIdx = bookCategoryMapper.selectOneBsListByBsIdx(bsIdx);
        return ResponseEntity.ok(bsNameByBsIdx);
    }

    @GetMapping("bssListByBsIdx")
    @Operation(summary = "선택한 중분류의 소분류 리스트", description = "호출 시 메인페이지에서 선택한 중분류의 bsIdx를 보내줄 것")
    public ResponseEntity<List<BookCategoryVo>> bssListByBsIdx(int bssIdx) {
        List<BookCategoryVo> bssListByBsIdx = bookCategoryMapper.selectBssList(bssIdx);
        return ResponseEntity.ok(bssListByBsIdx);
    }

    @GetMapping("bookListByBsIdx")
    @Operation(summary = "bsIdx를 이용해 책리스트 출력" , description = "호출 시 메인페이지에서 선택한 중분류의 bsIdx를 보내줄 것")
    public ResponseEntity<List<BookVo>> bookListByBsIdx(int bsIdx) {
        List<BookVo> bookListByBsIdx = bookMapper.selectBookListByBsIdx(bsIdx);
        return ResponseEntity.ok(bookListByBsIdx);
    }

    @GetMapping("bookOne")
    @Operation(summary = "isbn를 이용해 책의 정보 출력" , description = "호출 시 선택한 책의 bIdx을 보내줄 것")
    public ResponseEntity<BookVo> bookOne(int bIdx) {
        BookVo bookOneByIsbn = bookMapper.selectOneBookByBIdx(bIdx);
        bookService.bookImageService(bIdx);
        return ResponseEntity.ok(bookOneByIsbn);
    }

    @GetMapping("isBookmark")
    @Operation(summary = "북마크 확인" , description = "로그인된 사용자와 책의 번호를 확인해서 북마크를 체크하고, 북마크가 있는 경우 Y, 없는 경우 N을 반환")
    public ResponseEntity<String> isBookmark(int bIdx) {
        UserVo user = (UserVo) session.getAttribute("user");
        String isBookmark = "N";
        if (user != null) {
            int userIdx = user.getUserIdx();
            if(bookmarkMapper.selectOneUserBookmark(bIdx, userIdx)==null || bookmarkMapper.selectOneUserBookmark(bIdx, userIdx).equals("N")){
                return ResponseEntity.ok(isBookmark);
            } else if(bookmarkMapper.selectOneUserBookmark(bIdx, userIdx).equals("Y")){
                isBookmark = "Y";
            }
        }
        return ResponseEntity.ok(isBookmark);
    }

    @GetMapping("bookmark")
    @Operation(summary = "북마크 해제 및 추가", description = "북마크 버튼을 누르면 유저의 로그인 여부를 체크 후 토글로 북마크 처리 <br> map 이름은 bookmark")
    public ResponseEntity<Map<String, Object>> bookmark(int bIdx) {

        UserVo user = (UserVo) session.getAttribute("user");
        Map<String, Object> bookmark = new HashMap<>();

        if (user == null) {
            bookmark.put("message", "로그인필요.");
            return ResponseEntity.ok(bookmark);
        }

        int userIdx = user.getUserIdx();
        String nowBookmark = bookmarkMapper.selectOneUserBookmark(bIdx, userIdx);

        if (nowBookmark == null) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBIdx(bIdx);
            bookmarkVo.setIsBookmarked("Y");
            // System.out.println(bookmarkVo);
            int res = bookmarkMapper.insertBookmark(bookmarkVo);
            bookmark.put("message", "북마크추가완료");
        } else if (nowBookmark.equals("N")) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBIdx(bIdx);
            bookmarkVo.setIsBookmarked("Y");
            int res = bookmarkMapper.updateBookmark(bookmarkVo);
            bookmark.put("message", "북마크추가완료");
        } else if (nowBookmark.equals("Y")) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBIdx(bIdx);
            bookmarkVo.setIsBookmarked("N");
            int res = bookmarkMapper.updateBookmark(bookmarkVo);
            bookmark.put("message", "북마크해제완료");
        }

        return ResponseEntity.ok(bookmark);
    }

}
