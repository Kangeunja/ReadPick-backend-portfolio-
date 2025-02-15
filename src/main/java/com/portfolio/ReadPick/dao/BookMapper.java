package com.portfolio.ReadPick.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.BookmarkVo;

@Mapper
public interface BookMapper {

    List<BookVo> selectSearchList();

    void isbnInsert(BookVo book);

    List<BookVo> selectIsbnList();

    void insertBook(BookVo bookVo);

    int selectMaxBIdx();

    int selectMaxIsbnIdx();
    
    String selectIsbnByIsbnIdx(int isbnIdx);

    // mainPage
    // List<String> selectBmNameList();

    // List<String> selectBsNameList();

    List<BookVo> selectBookListByBsIdx(int bsIdx);

    BookVo selectOneBookByIsbn(String isbn);

    BookVo selectOneBookByBIdx(int bIdx);
    

}
