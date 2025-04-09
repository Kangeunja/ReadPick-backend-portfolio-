package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class BookImageController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookImageMapper bookImageMapper;

    @GetMapping("bookImageOne")
    @Operation(summary = "상세페이지에 띄울 유저가 선택한 책 하나의 이미지")
    public ResponseEntity<BookImageVo> bookImageOne(int bookIdx) {
        BookVo bookOneByIsbn = bookMapper.selectOneBookByBookIdx(bookIdx);
        BookImageVo image = bookImageMapper.selectOneImageByBookIdx(bookOneByIsbn.getBookIdx());
        return ResponseEntity.ok(image);
    }

    @GetMapping("bsImageList")
    @Operation(summary = "유저가 선택한 중분류의 이미지리스트")
    public ResponseEntity<List<BookImageVo>> bsImageList(int bsIdx) {
        List<BookImageVo> bsImageList = bookImageMapper.selectBsImageByBsIdx(bsIdx);
        return ResponseEntity.ok(bsImageList);
    }

    @GetMapping("bssImageList")
    @Operation(summary = "유저가 선택한 소분류의 이미지리스트")
    public ResponseEntity<List<BookImageVo>> bssImageList(int bssIdx) {
        List<BookImageVo> bssImageList = bookImageMapper.selectBssImageByBssIdx(bssIdx);
        return ResponseEntity.ok(bssImageList);
    }

    @GetMapping("bsssImageList")
    @Operation(summary = "유저가 선택한 세부분류의 이미지리스트")
    public ResponseEntity<List<BookImageVo>> bsssImageList(int bsssIdx) {
        List<BookImageVo> bsssImageList = bookImageMapper.selectBsssImageByBsssIdx(bsssIdx);
        return ResponseEntity.ok(bsssImageList);
    }
    

    @GetMapping("userGenreBookImage")
    public ResponseEntity<List<BookImageVo>> userGenreBookImage(Object bookIdx) {
        List<BookImageVo> imageList = bookImageMapper.selectImageByBookIdx((int) bookIdx);
        return ResponseEntity.ok(imageList);
    }

    // @GetMapping("todayBookImage")
    // @Operation(summary = "오늘의 책 이미지")
    // public ResponseEntity<String> todayBookImage(@RequestParam String param) {

    //     return ResponseEntity.ok(param);
    // }
    

}
