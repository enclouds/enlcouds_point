package com.enclouds.enpoint.app.service;

import com.enclouds.enpoint.app.dto.AppDto;

public interface AppService {

    int login(AppDto appDto) throws Exception;

    int getAuthCodeAjax(AppDto appDto) throws Exception;

    int getAuthCodeChkAjax(AppDto appDto) throws Exception;

    int setPasswordAjax(AppDto appDto) throws Exception;
}
