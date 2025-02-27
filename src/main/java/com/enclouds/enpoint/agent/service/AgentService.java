package com.enclouds.enpoint.agent.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.user.dto.PointDto;

import java.util.List;

public interface AgentService {

    List<AgentDto> selectAgentList(AgentDto agentDto) throws Exception;

    List<AgentDto> selectSubAdminAgentList(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentTotalList(AgentDto agentDto) throws Exception;

    int selectDuplAgent(AgentDto agentDto) throws Exception;

    int insertAgent(AgentDto agentDto) throws Exception;

    int updateAgent(AgentDto agentDto) throws Exception;

    int deleteAgent(AgentDto agentDto) throws Exception;

    int updateAgentAddPoint(AgentDto agentDto) throws Exception;

    int updateSubAdminAgentAddPoint(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket2(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket3(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket4(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket5(AgentDto agentDto) throws Exception;

    int updateAgentMinusPoint(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket2(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket3(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket4(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket5(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentPointHistory(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory2(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory3(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory4(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory5(AgentDto agentDto) throws Exception;

    AgentDto selectAgentInfo(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentTotalListAsAG() throws Exception;

    List<AgentDto> selectAgentPointList(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentSubPointList(AgentDto agentDto) throws Exception;

}
