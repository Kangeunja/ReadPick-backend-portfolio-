package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("rub")
public class ReviewUserBookDTO {
    int rvIdx;
    int userIdx;
    int bookIdx;
    String nickName;
    String content;
    String regDate;
    String fileName;

    String bookName;
    String author;
    String bookImageName;

}
