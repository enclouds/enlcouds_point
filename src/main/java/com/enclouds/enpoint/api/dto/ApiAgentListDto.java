package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiAgentListDto extends ApiRtnDto{

    private List<ApiAgentDto> agentList;

}
