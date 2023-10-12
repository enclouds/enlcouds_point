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

}
