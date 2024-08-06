package com.ecobank.app.admin.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.admin.mapper.AdminMapper;
import com.ecobank.app.admin.service.AdminService;
import com.ecobank.app.admin.service.UserVO;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public List<UserVO> UserList() {
        return adminMapper.userList();
    }

    @Override
    public int getcreaTeat() {
        return adminMapper.getcreaTeat();
    }

    @Override
    public int updateUserState(String useId, String userState) {
        return adminMapper.updateUserState(useId, userState);
    }
}
