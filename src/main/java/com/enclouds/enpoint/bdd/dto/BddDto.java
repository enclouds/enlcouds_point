package com.enclouds.enpoint.bdd.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class BddDto extends CommonDto {

    private int seq;
    private String title;
    private String content;
    private int clickCnt;
    private String regDate;
    private String newYn;

}
