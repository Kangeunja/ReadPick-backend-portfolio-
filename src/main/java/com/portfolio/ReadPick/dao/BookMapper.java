package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;

@Mapper
public interface BookMapper {

    List<BookVo> selectSearchList();

    void isbnInsert(BookVo book);

    List<BookVo> selectIsbnList();

    void insertBook(BookVo bookVo);

    int selectMaxBookIdx();

    int selectMaxIsbnIdx();
    
    String selectIsbnByIsbnIdx(int isbnIdx);

    // mainPage
    // List<String> selectBmNameList();

    // List<String> selectBsNameList();

    List<BookVo> selectBookListByBsIdx(int bsIdx);
    
    List<BookVo> selectBookListByBssIdx(int bssIdx);

    BookVo selectOneBookByIsbn(String isbn);

    BookVo selectBookByBookIdx(Object bookIdx);

    BookVo selectOneBookByBookIdx(int bookIdx);

    

}
