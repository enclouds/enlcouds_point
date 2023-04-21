package com.enclouds.enpoint.app.mapper;

import com.enclouds.enpoint.app.dto.AppDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AppMapper {

    int login(AppDto appDto) throws Exception;

    int updateAuthCode(AppDto appDto) throws Exception;

    int getAuthCodeChk(AppDto appDto) throws Exception;

    int setPassword(AppDto appDto) throws Exception;

}
