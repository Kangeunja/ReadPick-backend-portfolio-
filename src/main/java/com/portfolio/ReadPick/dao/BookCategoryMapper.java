package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookCategoryVo;

@Mapper
public interface BookCategoryMapper {


    Integer insertBmCategories(String bmCategory);

    Integer insertBsCategories(BookCategoryVo bc);

    Integer insertBssCategories(BookCategoryVo bc);

    Integer insertBsssCategories(BookCategoryVo bc);


    int selectMaxBmIdx();

    int selectMaxBsIdx();

    int selectMaxBssIdx();

    int selectMaxBsssIdx();

    int selectBmIdxByName(String bmName);

    int selectBsIdxByName(String bsName);

    int selectBssIdxByName(String bssName);

    // List<BookCategoryVo> selectCategories();



}
