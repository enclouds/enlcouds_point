package com.enclouds.enpoint.game.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class GameDto extends CommonDto {

    private Integer seq;
    private String gameName;
    private String regDate;
    private String startStack;
    private String rebuyStack;
    private String rebuyStack2;
    private String maxEntry;
    private Integer startStackInt;
    private Integer rebuyStackInt;
    private String regUserId;
    private String startTime;

}
