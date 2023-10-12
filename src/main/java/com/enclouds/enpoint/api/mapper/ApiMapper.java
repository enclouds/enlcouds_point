package com.enclouds.enpoint.api.mapper;

import com.enclouds.enpoint.api.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ApiMapper {

    ApiUserRtnDto selectUserInfo(ApiDto apiDto) throws Exception;

    List<ApiPointHistoryDto> getPointHistory(ApiDto apiDto) throws Exception;

    int addPoint(ApiDto apiDto) throws Exception;

    List<ApiTicketHistoryDto> getTicketHistory1(ApiDto apiDto) throws Exception;
    List<ApiTicketHistoryDto> getTicketHistory2(ApiDto apiDto) throws Exception;
    List<ApiTicketHistoryDto> getTicketHistory3(ApiDto apiDto) throws Exception;

    int addTicket(ApiDto apiDto) throws Exception;

    int minusTicket(ApiDto apiDto) throws Exception;

    List<ApiRankDto> getRankList(ApiDto apiDto) throws Exception;

    List<ApiTicketRankDto> getTicketRankList(ApiDto apiDto) throws Exception;

    int addJackpot(ApiDto apiDto) throws Exception;
    int insertAddJackpot(ApiDto apiDto) throws Exception;

}
