package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.SearchMapper;
import com.portfolio.ReadPick.service.BookService;
import com.portfolio.ReadPick.vo.BookVo;

@RestController
@RequestMapping("api")
public class SearchController {

    @Autowired
    private SearchMapper searchMapper;

    @Autowired
    private BookService bookService;

    @GetMapping("bookNameSearch")
    public ResponseEntity<List<BookVo>> bookNameSearch(String bookName) {
        
        List<BookVo> bookList = new ArrayList<>();
        List<BookVo> bookIdxList = searchMapper.selectBookListByBookName(bookName);

        for (BookVo book : bookIdxList) {
            int bookIdx = book.getBookIdx();
            String image = bookService.userGenreBookImage(bookIdx);
            book.setBookImageName(image);
            bookList.add(book);
        }
        
        return ResponseEntity.ok(bookList);
    }

    @GetMapping("authorSearch")
    public ResponseEntity<List<BookVo>> authorSearch(String author) {
        
        List<BookVo> bookList = new ArrayList<>();
        List<BookVo> bookIdxList = searchMapper.selectBookListByAuthor(author);
        
        for (BookVo book : bookIdxList) {
            int bookIdx = book.getBookIdx();
            String image = bookService.userGenreBookImage(bookIdx);
            book.setBookImageName(image);
            bookList.add(book);
        }
        
        return ResponseEntity.ok(bookList);
    }

    // @GetMapping("isbnSearch")
    // public ResponseEntity<List<BookVo>> isbnSearch(String isbn) {
        
    //     return ResponseEntity.ok(searchMapper.selectBookListByIsbn(isbn));
    // }


}
