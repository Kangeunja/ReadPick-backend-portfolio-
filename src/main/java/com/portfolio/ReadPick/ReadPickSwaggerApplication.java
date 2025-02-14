package com.portfolio.ReadPick;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.dao.UserMapper;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@SpringBootApplication
public class ReadPickSwaggerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReadPickSwaggerApplication.class, args);
    }
}

@Tag(name = "readPick-api", description = "readPick-api")
@RestController
@RequestMapping("/api")
// @Hidden
class ExampleController {

    // @Autowired
    // BookMapper bookMapper;

    // @Autowired
    // BookCategoryMapper bookCategoryMapper;

    // @Autowired
    // BookImageMapper bookImageMapper;

    // @Autowired
    // BookmarkMapper bookmarkMapper;

    // @Autowired
    // UserMapper userMapper;

    // // mainPage에 출력할 서브카테고리들
    // @GetMapping("bsList")
    // @Operation(summary = "bsList 전체조회", description = "bsList : bookSubCategory의 전체가 담긴 데이터")
    // public List<BookCategoryVo> bsNameList() {

    //     return bookCategoryMapper.selectBsList();
    // }

    // // bsIdx로 책 리스트를 조회
    // @GetMapping("bookListByBsIdx")
    // @Operation(summary = "bsIdx를 이용해 해당되는 책리스트 전체조회", description = "bsIdx : bookSubCategory의 고유번호")
    // public List<BookVo> bookListByBsIdx(int bsIdx) {

    //     return bookMapper.selectBookListByBsName(bsIdx);
    // }

    // @GetMapping("bookOneByIsbn") //
    // @Operation(summary = "isbn을 이용해 책 한 권의 데이터전체조회", description = "isbn : 프론트에서 보내줄 데이터")
    // public BookVo bookOneByIsbn(String isbn) {

    //     return bookMapper.selectOneBookByIsbn(isbn);
    // }

    // @GetMapping("imageOneByBIdx")
    // @Operation(summary = "bIdx로 이미지정보 조회")
    // public BookImageVo imageOneByBIdx(int bIdx) {

    //     return bookImageMapper.selectOneImageByBIdx(bIdx);
    // }

    // @GetMapping("bookmark")
    // @Operation(summary = "북마크 추가/삭제", description = "로그인한 유저가 특정 게시물을 북마크 추가 또는 삭제할 수 있습니다.")
    // public Map<String, Object> bookmark(int bIdx, Integer user) {

    //     // UserVo user = (UserVo) session.getAttribute("user");
    //     Map<String, Object> bookmark = new HashMap<>();

    //     if (user == null) {
    //         bookmark.put("message", "로그인이 필요한 서비스입니다.");
    //         return bookmark;
    //     }

    //     // int userIdx = user.getUserIdx();
    //     String nowBookmark = bookmarkMapper.selectOneUserBookmark(bIdx, user);
    //     if (nowBookmark == null) {
    //         BookmarkVo bookmarkVo = new BookmarkVo();
    //         bookmarkVo.setUserIdx(user);
    //         bookmarkVo.setBIdx(bIdx);
    //         bookmarkVo.setIsBookmarked("Y");
    //         int res = bookmarkMapper.insertBookmark(bookmarkVo);
    //         bookmark.put("message", "북마크추가완료");
    //     } else if (nowBookmark.equals("N")) {
    //         BookmarkVo bookmarkVo = new BookmarkVo();
    //         bookmarkVo.setUserIdx(user);
    //         bookmarkVo.setBIdx(bIdx);
    //         bookmarkVo.setIsBookmarked("Y");
    //         int res = bookmarkMapper.updateBookmark(bookmarkVo);
    //         bookmark.put("message", "북마크추가완료");
    //     } else if (nowBookmark.equals("Y")) {
    //         BookmarkVo bookmarkVo = new BookmarkVo();
    //         bookmarkVo.setUserIdx(user);
    //         bookmarkVo.setBIdx(bIdx);
    //         bookmarkVo.setIsBookmarked("N");
    //         int res = bookmarkMapper.updateBookmark(bookmarkVo);
    //         bookmark.put("message", "북마크해제완료");
    //     }
    //     return bookmark;
    // }

    // //유저
    // @RequestMapping("userPick.do")
    // public ResponseEntity<Map<String, Object>> userPick() {
    //     List<BookCategoryVo> bsList = bookCategoryMapper.selectBsList();
    //     List<BookCategoryVo> bssList = new ArrayList<BookCategoryVo>();
    //     for (int i = 0; i < bsList.size(); i++) {
    //         bssList = bookCategoryMapper.selectBssList(bsList.get(i).getBsIdx());
    //     }
    //     Map<String, Object> categories = new HashMap<String, Object>();
    //     categories.put("bsList", bsList);
    //     categories.put("bssList", bssList);
    //     return ResponseEntity.ok(categories);
    // }

    // @RequestMapping("userPickResult.do")
    // @Operation(summary = "유저의 장르선택 저장", description = "처음 로그인한 유저가 고른 장르들을 저장")
    // public ResponseEntity<String> userPickResult(List<List<Integer>> userPick, Integer userIdx) {

    //     if(userIdx != null) {
    //         for (int i = 0; i < userPick.size(); i++) {
    //             int bsIdx = userPick.get(i).get(0);
    //             int bssIdx = userPick.get(i).get(1);
    //             int bmIdx = bookCategoryMapper.selectBmIdxOneByBsIdx(bsIdx);
    
    //             int res = bookCategoryMapper.insertUserPick(userIdx, bmIdx, bsIdx, bssIdx);
    //         }
    //     }
    //     UserVo user = new UserVo();
    //     user.setUserIdx(userIdx);
    //     user.setFirstAt("N");
    //     int res = userMapper.userFirstAtUpdate(user);
        
    //     return ResponseEntity.ok("success");
    // }




}
