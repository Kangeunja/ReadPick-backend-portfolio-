package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("bss")
public class BssVo {

    int bsIdx;
    int bssIdx;
    String bssName;

}
