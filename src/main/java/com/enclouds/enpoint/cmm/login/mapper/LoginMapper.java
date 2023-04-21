package com.enclouds.enpoint.cmm.login.mapper;

import com.enclouds.enpoint.user.dto.LoginDto;
import com.enclouds.enpoint.user.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

    void saveLoginHist(UserDto userDto) throws Exception;

    LoginDto selectPaymoaLoginInfo(LoginDto loginDto) throws Exception;

}
