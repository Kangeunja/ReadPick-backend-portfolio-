package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("ru")
public class ReviewUserVo {
    int rvIdx;
    int userIdx;
    int bookIdx;
    String nickName;
    String content;
    String regDate;
    String fileName;
}
