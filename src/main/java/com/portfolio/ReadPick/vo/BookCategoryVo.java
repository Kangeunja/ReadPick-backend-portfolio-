package com.portfolio.ReadPick.vo;

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
}
