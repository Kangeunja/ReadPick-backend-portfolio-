package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("adminReview")
public class AdminReviewVo {
    int rvIdx;
    int bookIdx;
    int userIdx;
    int reportCount;
    String content;
    String regDate;
}
