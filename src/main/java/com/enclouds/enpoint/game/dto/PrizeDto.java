package com.enclouds.enpoint.game.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class PrizeDto extends CommonDto {

    private Integer seq;
    private Integer gameSeq;
    private Integer rank;
    private Integer prize;

}
