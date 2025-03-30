package com.enclouds.enpoint.user.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class TicketBuyDto extends CommonDto {

    private int seq;
    private Integer agentCode;
    private String agentName;
    private String regDate;
    private int buyCnt;
    private int sellCnt;
    private String ticketGbn;
    private String ticketSellGbn;
    private Integer pointInt;
    private Integer totalAmt;
    private String ticketGbnStr;
    private String gbn;
    private String nickName;
    private String phoneNum;
    private String phoneNumMasking;
    private int totalMinusTicket;
    private int totalAddTicket;
    private String preTicketCnt;

    private double minusTicket;
    private double addTicket;
    private String ticketGbnNm;
    private String ticketKindNm;

}
