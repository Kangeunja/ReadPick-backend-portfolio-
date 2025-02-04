package com.portfolio.ReadPick.vo;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("user")
public class UserVo {

    int userIdx;
    String userName;
    String nickName;
    String Id;
    String Password;
    String email;
    String adminAt; //Y/N


}
