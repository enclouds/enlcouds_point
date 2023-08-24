package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiTicketHistoryListDto extends ApiRtnDto{

    private List<ApiTicketHistoryDto> ticketHistoryList;

}
