package com.enclouds.enpoint.api.service;

import com.enclouds.enpoint.api.dto.*;

import java.util.List;

public interface ApiService {

    ApiUserRtnDto selectUserInfo(ApiDto apiDto) throws Exception;

    List<ApiPointHistoryDto> getPointHistory(ApiDto apiDto) throws Exception;

    int addPoint(ApiDto apiDto) throws Exception;

    int minusPoint(ApiDto apiDto) throws Exception;

    List<ApiTicketHistoryDto> getTicketHistory(ApiDto apiDto) throws Exception;

    int addTicket(ApiDto apiDto) throws Exception;

    int minusTicket(ApiDto apiDto) throws Exception;

    int addCoupon(ApiDto apiDto) throws Exception;

    int addWinPoint(ApiDto apiDto) throws Exception;

    List<ApiRankDto> getRankList(ApiDto apiDto) throws Exception;

    List<ApiTicketRankDto> getTicketRankList(ApiDto apiDto) throws Exception;

}
