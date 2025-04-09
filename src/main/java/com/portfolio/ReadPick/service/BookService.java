package com.portfolio.ReadPick.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.vo.BookImageVo;

@Service
public class BookService {

    @Autowired
    BookImageMapper bookImageMapper;

    public BookImageVo bookImageService(int bookIdx){
        return bookImageMapper.selectOneImageByBookIdx(bookIdx);
    }

    public String userGenreBookImage(Object bookIdx) {
        String image = bookImageMapper.selectImageOneByBookIdx((int) bookIdx);
        return image;
    }
    
}
