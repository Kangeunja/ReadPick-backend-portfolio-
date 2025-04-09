package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookImageVo;

@Mapper
public interface BookImageMapper {

    int insertFile(BookImageVo bookImageVo);

    BookImageVo selectOneImageByBookIdx(int bookIdx);

    List<BookImageVo> selectImageByBookIdx(int bookIdx);

    List<BookImageVo> selectBsImageByBsIdx(int bsIdx);

    List<BookImageVo> selectBssImageByBssIdx(int bssIdx);

    List<BookImageVo> selectBsssImageByBsssIdx(int bsssIdx);

    String selectImageOneByBookIdx(int bookIdx);

}
