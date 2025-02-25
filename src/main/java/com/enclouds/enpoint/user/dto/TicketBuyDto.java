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
    private String ticketGbn;
    private Integer pointInt;
    private Integer totalAmt;
    private String ticketGbnStr;

}
