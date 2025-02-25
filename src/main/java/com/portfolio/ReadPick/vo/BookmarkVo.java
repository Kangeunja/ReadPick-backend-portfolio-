package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bookmark")
public class BookmarkVo {

    int userIdx;
    int bookIdx;
    String isBookmarked;
}
