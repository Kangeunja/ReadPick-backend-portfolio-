package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookmarkVo;

@Mapper
public interface BookmarkMapper {

    int insertBookmark(BookmarkVo bookmarkVo);

    String selectOneUserBookmark(int bookIdx, int userIdx);

    int updateBookmark(BookmarkVo bookmarkVo);

    // int deleteBookmark(BookmarkVo bookmarkVo);

}
