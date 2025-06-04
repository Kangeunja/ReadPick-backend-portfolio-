package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.AdminReviewVo;
import com.portfolio.ReadPick.vo.AdminUserVo;
import com.portfolio.ReadPick.vo.BookVo;

@Mapper
public interface AdminMapper {

    // User List
    List<AdminUserVo> userList();

    List<Integer> selectAdminReviewIdxList();
    
    List<AdminReviewVo> selectAdminReviewFromIdxList(List<Integer> rvIdxList);

    List<BookVo> selectAdminBookList();

    int reviewDelete(int rvIdx);

    int deleteBook(int bookIdx);

	int reviewReset(int rvIdx);

    List<Integer> selectReportCount();

    int userDelete(int userIdx);
}
