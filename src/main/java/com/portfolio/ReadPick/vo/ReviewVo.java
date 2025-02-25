package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("review")
public class ReviewVo {
    int bookIdx;
    int userIdx;
    String content;
    String regDate;
    String reviewAt;
    
}
