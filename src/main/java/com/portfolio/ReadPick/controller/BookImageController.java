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

@RestController
public class BookImageController {

    @Autowired
    BookMapper bookMapper;

    @Autowired
    BookImageMapper bookImageMapper;

    @GetMapping("bookImageOne")
    public ResponseEntity<BookImageVo> bookImageOne(int bookIdx) {
        BookVo bookOneByIsbn = bookMapper.selectOneBookByBookIdx(bookIdx);
        BookImageVo image = bookImageMapper.selectOneImageByBookIdx(bookOneByIsbn.getBookIdx());
        return ResponseEntity.ok(image);
    }
    

    @GetMapping("userGenreBookImage")
    public ResponseEntity<List<BookImageVo>> userGenreBookImage(Object bookIdx) {
        List<BookImageVo> imageList = bookImageMapper.selectImageByBookIdx((int) bookIdx);
        return ResponseEntity.ok(imageList);
    }

}
