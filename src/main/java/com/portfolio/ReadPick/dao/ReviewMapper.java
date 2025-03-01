package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.ReviewVo;

@Mapper
public interface ReviewMapper {

    void insertReview(ReviewVo reviewVo);

    ReviewVo selectOneReview(int userIdx, int bookIdx);

    void reviewUpdate(ReviewVo reviewVo);

	void reviewDelete(int userIdx, int bookIdx);

	List<ReviewVo> selectReview(int bookIdx);

}
