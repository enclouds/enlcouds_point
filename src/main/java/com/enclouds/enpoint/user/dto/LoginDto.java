package com.enclouds.enpoint.user.dto;

import lombok.Data;

/**
 * 로그인 정보
 * @author Enclouds
 * @since 2020.12.22
 */

@Data
public class LoginDto {

    private String loginId;
    private String username;
    private String password;
    private String loginIp;

}
