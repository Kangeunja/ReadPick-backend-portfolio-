package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.service.NaverSearchIsbnService;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookVo;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class BookController {

    @Autowired
    BookMapper bookMapper;

    @Autowired 
    BookCategoryMapper bookCategoryMapper;

    @Autowired
    NaverSearchIsbnService searchIsbn;

    @RequestMapping("DBData.do")
    public String DBData() {

        List<BookVo> searchList = bookMapper.selectSearchList();
        for (int i = 0; i < searchList.size(); i++) {
            String searchOneName = searchList.get(i).getKeywordName();
            searchIsbn.searchIsbnSave(searchOneName);
            // System.out.println(searchOneName);
        }
        System.out.println("=====책 저장 끝=====");
        return "home";
    }

    // bsName으로 책 리스트를 찾아와 bsCategory에 출력
    @RequestMapping("bookSubCategory.do")
    public String requestMethodName(int bsIdx, Model model) {

        List<BookCategoryVo> bsList = bookCategoryMapper.selectBsList();
        model.addAttribute("bsList", bsList);

        List<BookVo> bookListByBsName = bookMapper.selectBookListByBsName(bsIdx);
        model.addAttribute("bookListByBsName", bookListByBsName);

        return "bookSubCategory";
    }

    @RequestMapping("bookOne.do") // isbn 프론트에서 보내줄 것
    public String requestMethodName(String isbn, Model model) {
        BookVo bookVo = bookMapper.selectOneBookByIsbn(isbn);
        model.addAttribute("bookVo", bookVo);
        return "bookOne";
    }
    

    



}
