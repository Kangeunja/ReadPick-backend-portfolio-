package com.portfolio.ReadPick.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bs")
public class BsVo {
    int bmIdx;
    int bsIdx;
    String bsName;
    List<BssVo> bssList;
}
