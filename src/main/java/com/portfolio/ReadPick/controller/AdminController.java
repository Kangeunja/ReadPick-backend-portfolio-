package com.portfolio.ReadPick.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.portfolio.ReadPick.dao.AdminMapper;
import com.portfolio.ReadPick.vo.AdminUserVo;

import jakarta.servlet.http.HttpSession;

@RestController
public class AdminController {

    @Autowired
    HttpSession session;

    @Autowired
    AdminMapper adminMapper;

    @GetMapping("adminUserList")
    public ResponseEntity<List<AdminUserVo>> adminUserList() {

        List<AdminUserVo> userList = adminMapper.userList();
        
        return ResponseEntity.ok(userList);
    }

}
