package com.enclouds.enpoint.api.dto;

import lombok.Data;

@Data
public class ApiUserRtnDto extends ApiRtnDto{

    private String phoneNum;
    private String nickName;
    private Integer point;
    private Integer rankPoint;
    private Integer couponPoint;
    private Integer rankSumPoint;
    private Integer ticket;
    private Integer ticket2;
    private Integer ticket3;
    private Integer ticket4;
    private Integer ticket5;
    private Integer rank;
    private Integer totalRankCnt;
    private Integer agentCode;

}
