package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;

@Mapper
public interface BookMapper {

    List<BookVo> selectSearchList();

}
