package com.enclouds.enpoint.user.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

/**
 * 사용자 정보
 * @author Enclouds
 * @since 2020.12.11
 */

@Data
public class UserDto extends CommonDto {

    private Integer agentCode;
    private String id;
    private String password;
    private String agentName;
    private Integer agentUpCode;
    private String agentGbn;
    private String point;
    private String ticket;
    private String ticket2;
    private String ticket3;
    private String loginIp;
    private Integer pointInt;
    private Integer ticketInt;
    private String couponPoint;
    private String phoneNumMasking;

    private String phoneNum;
    private String nickName;
    private String userGbn;
    private String regDate;

    private String addPoint;
    private String minusPoint;

    private String addRankSumPoint;
    private String minusRankSumPoint;

    private String addTicket;
    private String minusTicket;

    private String addCouponPoint;
    private String minusCouponPoint;
    private String historyCouponCnt;
    private String totalCouponPoint;

    private String memo;

    private Integer orgPoint;
    private Integer defPoint;
    private Integer privateDefPoint;

    private Integer orgTicket;
    private Integer orgTicket2;
    private Integer orgTicket3;
    private Integer defTicket;
    private Integer privateDefTicket;

    private String maxDate;
    private Integer rankPoint;
    private String rankPointStr;
    private Integer rankSumPoint;
    private String rankSumPointStr;
    private String addRankPoint;
    private String minusRankPoint;
    private String ticketStr;
    private String ticketStr2;
    private String ticketStr3;

    private Integer orgCouponPoint;

    private Integer rank;
    private Integer totalRankCnt;

    private Integer ranking;

    private String couponGbn;

}
