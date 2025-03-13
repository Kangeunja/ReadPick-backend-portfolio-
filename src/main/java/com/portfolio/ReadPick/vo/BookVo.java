package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("book")
public class BookVo {
    int bookIdx;
    int bmIdx;  //메인카테고리
    int bsIdx;  //하위카테고리
    int bssIdx; //하위카테고리
    Integer bsssIdx;   //세부카테고리
    String bookName;
    String author;
    String bContent;
    String link;
    String publisher;
    String pubDate;
    String isbn;
    String bookImageName;

    int bsssIsbnIdx;


    // 검색용
    String keywordName;
    
}
