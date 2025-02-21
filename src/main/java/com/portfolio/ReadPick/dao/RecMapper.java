package com.portfolio.ReadPick.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.RecVo;

@Mapper
public interface RecMapper {

	String selectOneUserRec(int bIdx, int userIdx);

    int insertRec(RecVo recVo);

    int updateRec(RecVo recVo);

    int recCount(int bIdx);

    int recCountMax();

    List<Integer> selectBmIdxListByUserIdx(int bsIdx);

    List<Integer> selectBsIdxListByUserIdx(int bssIdx);

    List<Integer> selectBssIdxListByUserIdx(int bsssIdx);

    List<Integer> selectBsssIdxListByUserIdx(int userIdx);

    List<Integer> selectBIdxByCategoryIdx(int bmIdx, int bsIdx, int bssIdx, int bsssIdx);

    // List<BookVo> selectBookListByBIdx(List<Integer> bIdxLis

    Map<String, Object> recCountMaxByUserRecBIdxList(List<Integer> bIdxList);

}
