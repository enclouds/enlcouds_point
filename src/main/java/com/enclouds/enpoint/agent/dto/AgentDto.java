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
    private Integer ticket;
    private String ticketStr;
    private String addTicket;
    private String minusTicket;

}
