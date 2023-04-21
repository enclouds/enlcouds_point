package com.enclouds.enpoint.nurigo;

import lombok.Data;

@Data
public class KakaoDto {

    private String rcvNum;
    private String templateId;
    private String storeNm;
    private String addPoint;
    private String minusPoint;
    private String totalPoint;
    private String authCode;

}
