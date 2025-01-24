package com.portfolio.ReadPick.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookImageVo;

@Mapper
public interface BookImageMapper {

    void insertFile(BookImageVo bookImageVo);

}
