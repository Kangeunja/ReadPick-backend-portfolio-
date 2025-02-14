package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.UserVo;

@Mapper
public interface UserMapper {

    List<UserVo> selectList();

    UserVo selectOneFromId(String id);

    int userInsert(UserVo user);

	int userFirstAtUpdate(UserVo user);

    int userInfoModify(UserVo user);

}
