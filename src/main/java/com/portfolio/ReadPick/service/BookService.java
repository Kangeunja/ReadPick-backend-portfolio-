package com.portfolio.ReadPick.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.portfolio.ReadPick.controller.BookImageController;

@Service
public class BookService {

    @Autowired
    BookImageController bookImageController;

    public void bookImageService(String isbn){
        bookImageController.bookImageOne(isbn);
    }
}
