package com.enclouds.enpoint.game.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class BlindDto extends CommonDto {

    private Integer seq;
    private Integer gameSeq;
    private Integer lvl;
    private Integer time;
    private String breakYn;
    private Integer smallBlind;
    private Integer bigBlind;
    private Integer ante;

    private Integer breakTime;

}
