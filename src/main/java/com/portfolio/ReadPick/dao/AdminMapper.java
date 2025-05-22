package com.portfolio.ReadPick.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.portfolio.ReadPick.vo.AdminUserVo;

@Mapper
public interface AdminMapper {

    // User List
    List<AdminUserVo> userList();
}
