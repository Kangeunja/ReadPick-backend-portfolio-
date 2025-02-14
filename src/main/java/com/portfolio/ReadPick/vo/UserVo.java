package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("user")
public class UserVo {

    int userIdx;
    String userName;
    String nickName;
    String id;
    String pw;
    String email;
    String adminAt; //Y/N
    String firstAt; //Y/N


}
