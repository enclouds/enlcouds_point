package com.enclouds.enpoint.jackpot.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class JackpotDto extends CommonDto {

    private Integer seq;
    private String jackpotTitle;
    private double jackpotPrize;
    private double addJackpotPrize;
    private double minusJackpotPrize;
    private String useYn;
    private String regDate;
    private String storeGbn;
    private String agentName;

    private int jackpotSeq;
    private String alertContent;
    private String alertYn;
    private int regAgentCode;
    private String jackpotGbn;

    private int orderNo;
    private String content;

}