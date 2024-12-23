package com.enclouds.enpoint.user.dto;

import lombok.Data;

@Data
public class RankDto {

    private Integer seq;
    private String agentName;
    private int agentCode;
    private String rankGbnNm;
    private String rankGbn;
    private int rank;
    private String phoneNum;
    private int privateDefRank;
    private String regDate;

}
