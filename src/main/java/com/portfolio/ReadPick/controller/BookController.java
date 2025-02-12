package com.portfolio.ReadPick.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.service.NaverSearchIsbnService;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpSession;

@Controller
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
    public String requestMethodName(String isbn, Model model, HttpSession session) {
        BookVo bookVo = bookMapper.selectOneBookByIsbn(isbn);
        model.addAttribute("bookVo", bookVo);

        UserVo user = (UserVo) session.getAttribute("user");
        int bIdx = bookVo.getBIdx();
        if (user != null) {
            int userIdx = user.getUserIdx();
            String isBookmark = bookmarkMapper.selectOneUserBookmark(bIdx, userIdx);
            if (isBookmark == null) {
                isBookmark = "N";
            }
            model.addAttribute("isBookmark", isBookmark);
        }
        BookImageVo image = bookImageMapper.selectOneImageByBIdx(bIdx);
        model.addAttribute("image", image);

        return "bookOne";
    }

    @RequestMapping("bookmark.do")
    @ResponseBody
    @Hidden
    public Map<String, Object> bookmark(int bIdx, HttpSession session) {

        UserVo user = (UserVo) session.getAttribute("user");
        Map<String, Object> bookmark = new HashMap<>();

        if (user == null) {
            bookmark.put("message", "로그인이 필요한 서비스입니다.");
            return bookmark;
        }

        int userIdx = user.getUserIdx();
        String nowBookmark = bookmarkMapper.selectOneUserBookmark(bIdx, userIdx);

        if (nowBookmark == null) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBIdx(bIdx);
            bookmarkVo.setIsBookmarked("Y");
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

        return bookmark;
    }

}
