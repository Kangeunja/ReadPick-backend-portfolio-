package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookImageVo;

@Mapper
public interface BookImageMapper {

    int insertFile(BookImageVo bookImageVo);

    BookImageVo selectOneImageByBIdx(int bIdx);

    List<BookImageVo> selectImageByBIdx(int bIdx);

}
