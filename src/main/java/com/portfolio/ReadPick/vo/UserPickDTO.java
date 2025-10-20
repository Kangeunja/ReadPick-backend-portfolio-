package com.portfolio.ReadPick.vo;

import java.util.List;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("up")
public class UserPickDTO {

    int userIdx;
    List<BmVo> bmList;
}
