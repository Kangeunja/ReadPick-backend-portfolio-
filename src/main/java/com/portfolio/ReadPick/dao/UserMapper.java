package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.UserImageVo;
import com.portfolio.ReadPick.vo.UserSessionDTO;
import com.portfolio.ReadPick.vo.UserVo;

@Mapper
public interface UserMapper {

    List<UserVo> selectList();

    UserVo selectOneFromId(String id);

    int userInsert(UserVo user);

	int userFirstAtUpdate(UserSessionDTO user);

	void insertUserImage(UserImageVo userImage);

    int deleteUserImage(int userIdx);

    String selectUserImageFromUserIdx(int userIdx);

    void updateUserImage(UserImageVo userImage);

    String selectPwFromUserIdx(int userIdx);

}
