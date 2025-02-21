package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;

@Mapper
public interface SearchMapper {

    List<BookVo> selectBookListByBName(String bName);

    List<BookVo> selectBookListByAuthor(String author);

    List<BookVo> selectBookListByIsbn(String isbn);



}
