package com.portfolio.ReadPick.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bc")
public class BookCategoryVo {
    
    int bmIdx;
    int bsIdx;
    int bssIdx;
    int bsssIdx;
    
    String bmName;
    String bsName;
    String bssName;
    String bsssName;
    
    String bmIsbn;

    public BookCategoryVo() {}
    public BookCategoryVo(BookCategoryVo bs, List<BookCategoryVo> bssList) {

    }
}
