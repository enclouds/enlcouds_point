package com.enclouds.enpoint.agent.mapper;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.user.dto.PointDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AgentMapper {

    List<AgentDto> selectAgentList(AgentDto agentDto) throws Exception;

    List<AgentDto> selectSubAdminAgentList(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentTotalList(AgentDto agentDto) throws Exception;

    int selectAgentListTotalCount(AgentDto agentDto) throws Exception;

    int selectDuplAgent(AgentDto agentDto) throws Exception;

    int insertAgent(AgentDto agentDto) throws Exception;

    int updateAgent(AgentDto agentDto) throws Exception;

    int deleteAgent(AgentDto agentDto) throws Exception;

    int updateAgentAddPoint(AgentDto agentDto) throws Exception;

    int selectSubAdminPoint() throws Exception;

    int updateSubAgentMinusPoint(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket2(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket3(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket4(AgentDto agentDto) throws Exception;

    int updateAgentAddTicket5(AgentDto agentDto) throws Exception;

    int insertAddAgentPoint(AgentDto agentDto) throws Exception;

    int insertAddTopAgentPoint(AgentDto agentDto) throws Exception;

    int insertAddSubAgentPoint(AgentDto agentDto) throws Exception;

    int insertAddAgentTicket(AgentDto agentDto) throws Exception;

    int insertAddAgentTicket2(AgentDto agentDto) throws Exception;

    int insertAddAgentTicket3(AgentDto agentDto) throws Exception;

    int insertAddAgentTicket4(AgentDto agentDto) throws Exception;

    int insertAddAgentTicket5(AgentDto agentDto) throws Exception;

    int updateAgentMinusPoint(AgentDto agentDto) throws Exception;

    int insertMinusAgentPoint(AgentDto agentDto) throws Exception;

    int insertMinusTopAgentPoint(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket2(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket3(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket4(AgentDto agentDto) throws Exception;

    int updateAgentMinusTicket5(AgentDto agentDto) throws Exception;

    int insertMinusAgentTicket(AgentDto agentDto) throws Exception;

    int insertMinusAgentTicket2(AgentDto agentDto) throws Exception;

    int insertMinusAgentTicket3(AgentDto agentDto) throws Exception;

    int insertMinusAgentTicket4(AgentDto agentDto) throws Exception;

    int insertMinusAgentTicket5(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentPointHistory(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory2(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory3(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory4(AgentDto agentDto) throws Exception;

    List<PointDto> selectAgentTicketHistory5(AgentDto agentDto) throws Exception;

    AgentDto selectAgentInfo(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentTotalListAsAG() throws Exception;

    List<AgentDto> selectAgentPointList(AgentDto agentDto) throws Exception;

    int selectAgentPointListTotalCount(AgentDto agentDto) throws Exception;

    List<AgentDto> selectAgentSubPointList(AgentDto agentDto) throws Exception;

    int selectAgentSubPointListTotalCount(AgentDto agentDto) throws Exception;

}
