package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("book")
public class BookVo {
    int bIdx;
    int bmIdx;  //메인카테고리
    int bsIdx;  //하위카테고리
    int bssIdx; //하위카테고리
    int bsssIdx;   //세부카테고리
    String bName;
    String author;
    String bContent;
    String link;
    String publisher;
    int pubDate;
    String isbn;


    // 검색용
    String keywordName;
    
}
