package com.enclouds.enpoint.app.service;

import com.enclouds.enpoint.app.dto.AppDto;
import com.enclouds.enpoint.app.mapper.AppMapper;
import com.enclouds.enpoint.nurigo.KakaoDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.CustomUserService;
import net.nurigo.sdk.KakaoExampleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class AppServiceImpl implements AppService{

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private CustomUserService customUserService;

    @Override
    public int login(AppDto appDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(appDto.getId());
        UserDto userInfo = customUserService.selectCustomUserInfo(userDto);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        int result = 0;
        if(passwordEncoder.matches(appDto.getPassword(), userInfo.getPassword())){
            result = 1;
        }else {
            result = -1;
        }

        return result;
    }

    @Override
    public int getAuthCodeAjax(AppDto appDto) throws Exception {
        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        int authCode = random.nextInt(1000000) % 1000000;
        appDto.setAuthCode(authCode);

        UserDto userDto = new UserDto();
        userDto.setPhoneNum(appDto.getPhoneNum());
        UserDto userInfo = customUserService.selectCustomUserInfo(userDto);
        int result = 0;
        if(userInfo != null){
            result = appMapper.updateAuthCode(appDto);
            if(result > 0){
                KakaoDto kakaoDto = new KakaoDto();
                kakaoDto.setTemplateId("KA01TP230308085912775kWORi72OAz5");
                kakaoDto.setRcvNum(appDto.getPhoneNum());
                kakaoDto.setAuthCode(String.valueOf(authCode));

                KakaoExampleController kakaoExampleController = new KakaoExampleController();
                kakaoExampleController.sendOneAta(kakaoDto);
            }
        }else {
            result = -2;
        }

        return result;
    }

    @Override
    public int setPasswordAjax(AppDto appDto) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        appDto.setPassword(passwordEncoder.encode(appDto.getPasswordStr()));

        return appMapper.setPassword(appDto);
    }

    @Override
    public int getAuthCodeChkAjax(AppDto appDto) throws Exception {
        return appMapper.getAuthCodeChk(appDto);
    }
}
