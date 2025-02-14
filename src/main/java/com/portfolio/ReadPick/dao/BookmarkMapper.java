package com.portfolio.ReadPick.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookmarkVo;

@Mapper
public interface BookmarkMapper {

    int insertBookmark(BookmarkVo bookmarkVo);

    String selectOneUserBookmark(int bIdx, int userIdx);

    int updateBookmark(BookmarkVo bookmarkVo);

    BookmarkVo selectMyPageBookList(int userIdx);

}
