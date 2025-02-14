package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookCategoryVo;

@Mapper
public interface BookCategoryMapper {


    Integer insertBmCategories(String bmName);

    Integer insertBsCategories(BookCategoryVo bc);

    Integer insertBssCategories(BookCategoryVo bc);

    Integer insertBsssCategories(BookCategoryVo bc);


    int selectBmIdxOne(String isbn);

    int selectBsIdxOne(String isbn);

    int selectBssIdxOne(String isbn);
    
    int selectBsssIdxOne(String isbn);

    int selectBmIdxByName(String bmName);

    int selectBsIdxByName(String bsName);

    int selectBssIdxByName(String bssName);

    int selectBsssIdxBybsssName(String bsssName);

    List<BookCategoryVo> selectBsList();

    List<BookCategoryVo> selectBssList(int bsIdx);

    int insertUserPick(int userIdx, int bmIdx, int bsIdx, int bssIdx);

    int selectBmIdxOneByBsIdx(int bsIdx);

}
