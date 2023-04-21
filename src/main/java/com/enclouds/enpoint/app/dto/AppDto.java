package com.enclouds.enpoint.app.dto;

import lombok.Data;

@Data
public class AppDto {

    private String id;
    private String password;
    private Integer bddSeq;
    private Integer authCode;
    private String phoneNum;
    private String passwordStr;

}
