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
    private String loginIp;
    private Integer pointInt;
    private Integer ticketInt;

    private String phoneNum;
    private String nickName;
    private String userGbn;
    private String regDate;

    private String addPoint;
    private String minusPoint;

    private String addTicket;
    private String minusTicket;

    private String addCouponPoint;
    private String minusCouponPoint;

    private String memo;

    private Integer orgPoint;
    private Integer defPoint;
    private Integer privateDefPoint;

    private Integer orgTicket;
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

    private String couponPoint;
    private Integer orgCouponPoint;

    private Integer rank;
    private Integer totalRankCnt;

}
