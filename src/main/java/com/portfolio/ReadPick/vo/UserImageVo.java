package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("userImage")
public class UserImageVo {
    int userIdx;
    int fileIdx;
    String fileName;
}
