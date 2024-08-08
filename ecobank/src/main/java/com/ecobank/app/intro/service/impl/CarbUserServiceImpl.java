package com.ecobank.app.intro.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecobank.app.intro.mapper.CarbUserMapper;
import com.ecobank.app.intro.service.CarbUserService;

@Service
public class CarbUserServiceImpl implements CarbUserService {

    private CarbUserMapper userMapper;

    @Autowired
    public CarbUserServiceImpl(CarbUserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public void updateCountryInfo(String userId, String countryCode) {
        userMapper.updateCountryInfo(userId, countryCode);
    }
}