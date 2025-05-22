package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("adminUser")
public class AdminUserVo {

    int userIdx;
    String id;
    String nickName;
    String userName;
    String email;
    String regDate;

}
