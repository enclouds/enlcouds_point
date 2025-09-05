package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiTicketChangePriceListDto extends ApiRtnDto{

    private List<ApiTicketAsPriceDto> ticketList;

}
