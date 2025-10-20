package com.portfolio.ReadPick.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bm")
public class BmVo {

    int bmIdx;
    String bmName;
    List<BsVo> bsList;

}
