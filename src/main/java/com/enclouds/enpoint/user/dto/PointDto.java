package com.enclouds.enpoint.user.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class PointDto extends CommonDto {

    private Integer seq;
    private String agentName;
    private String agentCode;
    private String pointGbnNm;
    private String pointGbn;
    private String ticketGbnNm;
    private String ticketGbn;
    private String point;
    private String ticket;
    private String regDate;
    private String phoneNum;
    private String phoneNumMasking;
    private String nickName;

    private int minusPoint;
    private int addPoint;

    private int pointInt;
    private int ticketInt;
    private String couponPoint;
    private String ticket1;
    private String ticket2;
    private String ticket3;

    private int totalMinusPoint;
    private int totalAddPoint;

    private int defPoint;
    private String defPointStr;
    private int privateDefPoint;
    private String privateDefPointStr;

}
