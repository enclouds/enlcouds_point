package com.enclouds.enpoint.agent.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class AgentDto extends CommonDto {

    private Integer agentCode;
    private Integer agentUpCode;
    private String id;
    private String password;
    private String agentName;
    private String memo;
    private String point;
    private String passwordStr;
    private String agentGbn;
    private String regDate;
    private String loginYn;
    private String addPoint;
    private String minusPoint;
    private Integer pointInt;
    private Integer ticketInt;
    private Integer ticketInt2;
    private Integer ticketInt3;
    private Integer ticket;
    private Integer ticket2;
    private Integer ticket3;
    private String ticketStr;
    private String ticketStr2;
    private String ticketStr3;
    private String addTicket;
    private String minusTicket;

    private Integer defPoint;

}
