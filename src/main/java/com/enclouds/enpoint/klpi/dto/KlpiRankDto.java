package com.enclouds.enpoint.klpi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class KlpiRankDto {

    private int rank;
    private double score;
    private String nickname;
    private String branch;

}
