package com.enclouds.enpoint.univ.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class UnivDto extends CommonDto {

    private String univId;
    private String univNm;
    private int univTicket;
    private int addTicket;
    private int minusTicket;
    private Integer agentCode;
    private Integer defTicket;
    private Integer privateDefTicket;

}
