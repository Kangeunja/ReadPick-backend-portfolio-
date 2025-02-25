package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bookImage")
public class BookImageVo {

    int bookIdx;
    int fileIdx;
    String fileName;
    String fileType;
}
