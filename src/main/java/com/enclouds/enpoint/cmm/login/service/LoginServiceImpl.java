package com.enclouds.enpoint.cmm.login.service;

import com.enclouds.enpoint.cmm.login.mapper.LoginMapper;
import com.enclouds.enpoint.user.dto.LoginDto;
import com.enclouds.enpoint.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService{

    @Autowired
    private LoginMapper loginMapper;

    @Override
    public void saveLoginHist(UserDto userDto) throws Exception {
        loginMapper.saveLoginHist(userDto);
    }

    @Override
    public LoginDto selectPaymoaLoginInfo(LoginDto loginDto) throws Exception {
        return loginMapper.selectPaymoaLoginInfo(loginDto);
    }
}
