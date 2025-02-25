package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("rec")
public class RecVo {
    int userIdx;
    int bookIdx;
    String isRecommended;
}
