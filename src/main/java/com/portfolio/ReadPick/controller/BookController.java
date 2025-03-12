package com.portfolio.ReadPick.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.BookCategoryMapper;
import com.portfolio.ReadPick.dao.BookImageMapper;
import com.portfolio.ReadPick.dao.BookMapper;
import com.portfolio.ReadPick.dao.BookmarkMapper;
import com.portfolio.ReadPick.dao.RecMapper;
import com.portfolio.ReadPick.service.BookService;
import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.BsVo;
import com.portfolio.ReadPick.vo.BssVo;
import com.portfolio.ReadPick.vo.RecVo;
import com.portfolio.ReadPick.vo.UserVo;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;


@RestController
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
    BookImageController bookImageController;

    @Autowired
    HttpSession session;

    @Autowired
    BookService bookService;

    @Autowired
    RecMapper recMapper;

    @GetMapping("bsList")
    @Operation(summary = "메인 페이지에 띄울 중분류 리스트")
    public ResponseEntity<List<BookCategoryVo>> bsList() {
        return ResponseEntity.ok(bookCategoryMapper.selectBsList());
    }
    
    

    @GetMapping("bssListByBsIdx")
    @Operation(summary = "메인 페이지에서 선택한 중분류의 소분류 리스트", description = "호출 시 메인페이지에서 선택한 중분류의 bsIdx를 보내줄 것")
    public ResponseEntity<List<BsVo>> bssListByBsIdx() {

        List<BookCategoryVo> bsList = bookCategoryMapper.selectBsList();
        List<BsVo> bsVoList = new ArrayList<BsVo>();
        for(BookCategoryVo bs : bsList) {
            int bsIdx = bs.getBsIdx();
            List<BssVo> bssListByBsIdx = bookCategoryMapper.selectBssListByBsIdx(bsIdx);
            BsVo bsVo = new BsVo();
            try {
                bsVo = bookCategoryMapper.selectOneBsByBsIdx(bsIdx);
                bsVo.setBssList(bssListByBsIdx);
                bsVoList.add(bsVo);
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        

        return ResponseEntity.ok(bsVoList);
    }

    @GetMapping("bookListByBsIdx")
    @Operation(summary = "bsIdx를 이용해 책리스트 출력", description = "호출 시 메인페이지에서 선택한 중분류의 bsIdx를 보내줄 것")
    public ResponseEntity<List<BookVo>> bookListByBsIdx(int bsIdx) {
        List<BookVo> bookListByBsIdx = bookMapper.selectBookListByBsIdx(bsIdx);
        return ResponseEntity.ok(bookListByBsIdx);
    }

    @GetMapping("bookListByBssIdx")
    @Operation(summary = "bssIdx를 이용해 책리스트 출력", description = "호출 시 메인페이지에서 선택한 소분류의 bssIdx를 보내줄 것")
    public ResponseEntity<List<BookVo>> bookListByBssIdx(int bssIdx) {
        List<BookVo> bookListByBssIdx = bookMapper.selectBookListByBssIdx(bssIdx);
        return ResponseEntity.ok(bookListByBssIdx);
    }

    @GetMapping("bookOne")
    @Operation(summary = "bookIdx를 이용해 책의 정보 출력", description = "호출 시 선택한 책의 bookIdx을 보내줄 것")
    public ResponseEntity<BookVo> bookOne(int bookIdx) {
        BookVo bookOneByBookIdx = bookMapper.selectOneBookByBookIdx(bookIdx);
        bookService.bookImageService(bookIdx);
        return ResponseEntity.ok(bookOneByBookIdx);
    }

    @GetMapping("isBookmark")
    @Operation(summary = "북마크 확인", description = "로그인된 사용자와 책의 번호를 확인해서 북마크를 체크하고, 북마크가 있는 경우 Y, 없는 경우 N을 반환")
    public ResponseEntity<String> isBookmark(int bookIdx) {
        UserVo user = (UserVo) session.getAttribute("user");
        String isBookmark = "N";
        if (user != null) {
            int userIdx = user.getUserIdx();
            if (bookmarkMapper.selectOneUserBookmark(bookIdx, userIdx) == null
                    || bookmarkMapper.selectOneUserBookmark(bookIdx, userIdx).equals("N")) {
                return ResponseEntity.ok(isBookmark);
            } else if (bookmarkMapper.selectOneUserBookmark(bookIdx, userIdx).equals("Y")) {
                isBookmark = "Y";
            }
        }
        return ResponseEntity.ok(isBookmark);
    }

    @PostMapping("bookmark")
    @Operation(summary = "북마크 해제 및 추가", description = "북마크 버튼을 누르면 유저의 로그인 여부를 체크 후 토글로 북마크 처리 <br> map 이름은 bookmark")
    public ResponseEntity<Map<String, Object>> bookmark(int bookIdx) {

        UserVo user = (UserVo) session.getAttribute("user");
        Map<String, Object> bookmark = new HashMap<>();

        if (user == null) {
            bookmark.put("message", "로그인필요.");
            return ResponseEntity.ok(bookmark);
        }

        int userIdx = user.getUserIdx();
        String nowBookmark = bookmarkMapper.selectOneUserBookmark(bookIdx, userIdx);

        if (nowBookmark == null) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBookIdx(bookIdx);
            bookmarkVo.setIsBookmarked("Y");
            // System.out.println(bookmarkVo);
            int res = bookmarkMapper.insertBookmark(bookmarkVo);
            bookmark.put("message", "북마크추가완료");
        } else if (nowBookmark.equals("N")) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBookIdx(bookIdx);
            bookmarkVo.setIsBookmarked("Y");
            int res = bookmarkMapper.updateBookmark(bookmarkVo);
            bookmark.put("message", "북마크추가완료");
        } else if (nowBookmark.equals("Y")) {
            BookmarkVo bookmarkVo = new BookmarkVo();
            bookmarkVo.setUserIdx(userIdx);
            bookmarkVo.setBookIdx(bookIdx);
            bookmarkVo.setIsBookmarked("N");
            int res = bookmarkMapper.updateBookmark(bookmarkVo);
            bookmark.put("message", "북마크해제완료");
        }

        return ResponseEntity.ok(bookmark);
    }

    // 책 추천 로직
    @PostMapping("recommend")
    @Operation(summary = "책 추천", description = "책 추천")
    public ResponseEntity<Map<String, Object>> recommend(int bookIdx) {

        UserVo user = (UserVo) session.getAttribute("user");
        Map<String, Object> rec = new HashMap<>();

        if (user == null) {
            rec.put("message", "로그인필요.");
            return ResponseEntity.ok(rec);
        }

        int userIdx = user.getUserIdx();
        String nowRec = recMapper.selectOneUserRec(bookIdx, userIdx);

        if (nowRec == null) {
            RecVo recVo = new RecVo();
            recVo.setUserIdx(userIdx);
            recVo.setBookIdx(bookIdx);
            recVo.setIsRecommended("Y");
            // System.out.println(recVo);
            int res = recMapper.insertRec(recVo);
            rec.put("message", "추천완료");
        } else if (nowRec.equals("N")) {
            RecVo recVo = new RecVo();
            recVo.setUserIdx(userIdx);
            recVo.setBookIdx(bookIdx);
            recVo.setIsRecommended("Y");
            ;
            int res = recMapper.updateRec(recVo);
            rec.put("message", "추천완료");
        } else if (nowRec.equals("Y")) {
            RecVo recVo = new RecVo();
            recVo.setUserIdx(userIdx);
            recVo.setBookIdx(bookIdx);
            recVo.setIsRecommended("N");
            int res = recMapper.updateRec(recVo);
            rec.put("message", "추천취소");
        }

        return ResponseEntity.ok(rec);

    }

    // 추천 카운트
    @GetMapping("recCount")
    @Operation(summary = "추천 수 체크", description = "사용 시 bookIdx(int)를 보내줄 것 ")
    public ResponseEntity<Integer> recCount(int bookIdx) {
        int recCount = recMapper.recCount(bookIdx);
        return ResponseEntity.ok(recCount);
    }

    @GetMapping("userGenreBook")
    @Operation(summary = "유저 장르 별 책 추천", description = "유저가 선택한 장르 중 가장 높은 추천을 받은 책 4개를 리턴")
    public ResponseEntity<List<BookVo>> userGenreBook() {

        UserVo user = (UserVo) session.getAttribute("user");
        int userIdx = user.getUserIdx();
        List<Integer> bssIdxList = recMapper.selectBssIdxListByUserIdx(userIdx);
        List<Integer> bsIdxList = recMapper.selectBsIdxListByUserIdx(userIdx);
        List<Integer> bmIdxList = recMapper.selectBmIdxListByUserIdx(userIdx);

        List<Integer> bookIdxList = new ArrayList<>();
        for (int i = 0; i < bssIdxList.size(); i++) {
            int bssIdx = bssIdxList.get(i);
            int bsIdx = bsIdxList.get(i);
            int bmIdx = bmIdxList.get(i);
            bookIdxList.addAll(recMapper.selectBookIdxByCategoryIdx(bmIdx, bsIdx, bssIdx));
        }

        List<Map<String, Object>> recCountMaxAndBookIdxListByList = recMapper.recCountMaxByUserRecBookIdxList(bookIdxList);
        if (recCountMaxAndBookIdxListByList == null) {
            return ResponseEntity.ok(null);
        }
        List<BookVo> bookListByBookIdx = new ArrayList<>();
        for (int i = 0; i < recCountMaxAndBookIdxListByList.size(); i++) {
            bookListByBookIdx.add(bookMapper.selectBookByBookIdx(recCountMaxAndBookIdxListByList.get(i).get("bookIdx")));
            bookImageController.userGenreBookImage(recCountMaxAndBookIdxListByList.get(i).get("bookIdx"));
        }
        return ResponseEntity.ok(bookListByBookIdx);
    }

    @GetMapping("todayBook")
    @Operation(summary = "오늘의 책", description = "오늘의 책 추천")
    public ResponseEntity<BookVo> todayBook() {
        Map<String, Object> bookIdxAndMaxCount = new HashMap<>();
        BookVo book = new BookVo();
        try {
            bookIdxAndMaxCount = recMapper.recCountMaxBook();
            int bookIdx = (int) bookIdxAndMaxCount.get("bookIdx");
            book = bookMapper.selectOneBookByBookIdx(bookIdx);
            bookImageController.bookImageOne(bookIdx);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.ok(null);
        }
        return ResponseEntity.ok(book);
    }

}
