package com.enclouds.enpoint.agent.mapper;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.user.dto.PointDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgentMapper {

    List<AgentDto> selectAgentList(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentTotalList(AgentDto agentDto) throws Exception;

    int selectAgentListTotalCount(AgentDto agentDto) throws Exception;

    int selectDuplAgent(AgentDto agentDto) throws Exception;

    int insertAgent(AgentDto agentDto) throws Exception;

    int updateAgent(AgentDto agentDto) throws Exception;

    int deleteAgent(AgentDto agentDto) throws Exception;

    int updateAgentAddPoint(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket(AgentDto agentDto) throws Exception;

    int insertAddAgentPoint(AgentDto agentDto) throws Exception;

    int insertAddAgentTicket(AgentDto agentDto) throws Exception;

    int updateAgentMinusPoint(AgentDto agentDto) throws Exception;

    int insertMinusAgentPoint(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket(AgentDto agentDto) throws Exception;

    int insertMinusAgentTicket(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentPointHistory(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory(AgentDto agentDto) throws Exception;

    AgentDto selectAgentInfo(AgentDto agentDto) throws Exception;

}
