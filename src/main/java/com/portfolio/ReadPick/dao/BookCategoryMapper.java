package com.portfolio.ReadPick.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookCategoryVo;
import com.portfolio.ReadPick.vo.BsVo;
import com.portfolio.ReadPick.vo.BssVo;

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

    BsVo selectOneBsByBsIdx(int bsIdx);

    // BookCategoryVo selectBsOne(int bsIdx);

    // BookCategoryVo selectBssOne(int bssIdx);

    List<BsVo> selectCategoryView();

    List<BookCategoryVo> selectUserPick();

    List<BssVo> selectBssListByBsIdx(int bsIdx);

}
