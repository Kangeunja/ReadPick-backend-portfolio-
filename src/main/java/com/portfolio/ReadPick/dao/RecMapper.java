package com.portfolio.ReadPick.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.RecVo;

@Mapper
public interface RecMapper {

	String selectOneUserRec(int bIdx, int userIdx);

    int insertRec(RecVo recVo);

    int updateRec(RecVo recVo);

    int recCount(int bIdx);

    int recCountMax();

}
