package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiTicketRankListDto extends ApiRtnDto{

    private List<ApiTicketRankDto> ticketRankList;

}
