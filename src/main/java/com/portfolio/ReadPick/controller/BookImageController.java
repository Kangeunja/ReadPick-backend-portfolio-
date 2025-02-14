package com.portfolio.ReadPick.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;

@RestController
public class BookImageController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookImageMapper bookImageMapper;

    @GetMapping("bookImageOne")
    public ResponseEntity<BookImageVo> bookImageOne(String isbn) {
        BookVo bookOneByIsbn = bookMapper.selectOneBookByIsbn(isbn);
        BookImageVo image = bookImageMapper.selectOneImageByBIdx(bookOneByIsbn.getBIdx());
        return ResponseEntity.ok(image);
    }

}
