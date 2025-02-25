package com.portfolio.ReadPick.dao;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.ReviewVo;

@Mapper
public interface ReviewMapper {

    void insertReview(ReviewVo reviewVo);

    ReviewVo selectOneReview(int userIdx, int bookIdx);

}
