package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookImageVo;
import com.portfolio.ReadPick.vo.BookmarkVo;
import com.portfolio.ReadPick.vo.UserVo;

@Mapper
public interface MyPageMapper {

    int userInfoModify(UserVo user);

    List<BookmarkVo> bookmarkList(BookmarkVo bookmarkVo);

	BookImageVo bookmarkImageList(int bookIdx);

}
