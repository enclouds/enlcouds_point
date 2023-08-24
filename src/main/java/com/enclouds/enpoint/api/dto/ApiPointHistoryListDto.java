package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiPointHistoryListDto extends ApiRtnDto{

    private List<ApiPointHistoryDto> pointHistoryList;

}
