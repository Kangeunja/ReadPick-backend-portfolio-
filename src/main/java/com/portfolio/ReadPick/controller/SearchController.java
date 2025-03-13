package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.SearchMapper;
import com.portfolio.ReadPick.vo.BookVo;

@RestController
public class SearchController {

    @Autowired
    private SearchMapper searchMapper;

    @GetMapping("bookNameSearch")
    public ResponseEntity<List<BookVo>> bookNameSearch(String bookName) {
        
        return ResponseEntity.ok(searchMapper.selectBookListByBookName(bookName));
    }

    @GetMapping("authorSearch")
    public ResponseEntity<List<BookVo>> authorSearch(String author) {
        
        return ResponseEntity.ok(searchMapper.selectBookListByAuthor(author));
    }

    @GetMapping("isbnSearch")
    public ResponseEntity<List<BookVo>> isbnSearch(String isbn) {
        
        return ResponseEntity.ok(searchMapper.selectBookListByIsbn(isbn));
    }


}
