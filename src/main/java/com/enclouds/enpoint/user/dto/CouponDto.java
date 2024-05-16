package com.enclouds.enpoint.user.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class CouponDto extends CommonDto {

    private Integer seq;
    private String couponGbn;
    private String couponCnt;
    private String preCouponCnt;
    private String regDate;
    private String agentName;

    private String agentCode;
    private String couponGbnNm;

    private double minusCoupon;
    private double addCoupon;

    private String phoneNum;
    private String phoneNumMasking;
    private String nickName;

    private int totalMinusCoupon;
    private int totalAddCoupon;

}
