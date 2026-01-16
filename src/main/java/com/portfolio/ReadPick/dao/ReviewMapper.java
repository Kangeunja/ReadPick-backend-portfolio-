package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.BookVo;
import com.portfolio.ReadPick.vo.ReviewUserBookDTO;
import com.portfolio.ReadPick.vo.ReviewUserVo;
import com.portfolio.ReadPick.vo.ReviewVo;

@Mapper
public interface ReviewMapper {

    void insertReview(ReviewVo reviewVo);

    ReviewVo selectOneReview(int userIdx, int bookIdx);

    void reviewUpdate(ReviewVo reviewVo);

	void reviewDelete(int userIdx, int bookIdx);

	List<ReviewUserVo> selectReview(int bookIdx);

    BookVo selectBookByRvIdx(int rvIdx);

    List<ReviewUserVo> selectReviewMore(int bookIdx, int rvIdx);

    int insertReportReview(int rvIdx, int userIdx);

    int reviewCount(int bookIdx);

    List<ReviewUserVo> selectMyReview(int userIdx);

    int selectMyReviewCount(int userIdx);

    List<ReviewUserVo> selectMyReviewMore(int userIdx, int rvIdx);

    ReviewUserBookDTO selectBookByRvIdxForUser(int rvIdx);


}
