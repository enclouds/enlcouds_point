package com.enclouds.enpoint.cmm.login.service;

import com.enclouds.enpoint.user.dto.LoginDto;
import com.enclouds.enpoint.user.dto.UserDto;

public interface LoginService {

    void saveLoginHist(UserDto userDto) throws Exception;

    LoginDto selectPaymoaLoginInfo(LoginDto loginDto) throws Exception;

}
