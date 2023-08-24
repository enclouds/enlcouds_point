package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiRankListDto extends ApiRtnDto{

    private List<ApiRankDto> rankList;

}
