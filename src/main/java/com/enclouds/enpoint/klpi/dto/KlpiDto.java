package com.enclouds.enpoint.klpi.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class KlpiDto extends CommonDto {

    private Integer seq;
    private String gbn;
    private String phoneNum;
    private Double point;
    private String regUserId;
    private String tournamentSeq;

}
