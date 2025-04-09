package com.portfolio.ReadPick.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.RecVo;

@Mapper
public interface RecMapper {

	String selectOneUserRec(int bookIdx, int userIdx);

    int insertRec(RecVo recVo);

    int updateRec(RecVo recVo);

    int recCount(int bookIdx);

    int recCountMax();

    List<Integer> selectBmIdxListByUserIdx(int bsIdx);

    List<Integer> selectBsIdxListByUserIdx(int bssIdx);

    List<Integer> selectBssIdxListByUserIdx(int bsssIdx);

    List<Integer> selectBsssIdxListByUserIdx(int userIdx);

    List<Integer> selectBookIdxByCategoryIdx(int bmIdx, int bsIdx, int bssIdx);

    // List<BookVo> selectBookListByBookIdx(List<Integer> bookIdxLis

    List<Map<String, Object>> recCountMaxByUserRecBookIdxList(List<Integer> list);

    Map<String, Object> recCountMaxBook();
    
    String selectOneUserIsRec(int bookIdx, int userIdx);

}
