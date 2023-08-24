package com.enclouds.enpoint.agent.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.mapper.AgentMapper;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.user.dto.PointDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgentServiceImpl implements AgentService{

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public List<AgentDto> selectAgentList(AgentDto agentDto) throws Exception {
        int agentTotalCount = agentMapper.selectAgentListTotalCount(agentDto);

        PaginationInfo paginationInfo = new PaginationInfo(agentDto);
        paginationInfo.setTotalRecordCount(agentTotalCount);
        agentDto.setPaginationInfo(paginationInfo);

        return agentMapper.selectAgentList(agentDto);
    }

    @Override
    public List<AgentDto> selectAgentTotalList(AgentDto agentDto) throws Exception {
        return agentMapper.selectAgentTotalList(agentDto);
    }

    @Override
    public int selectDuplAgent(AgentDto agentDto) throws Exception {
        return agentMapper.selectDuplAgent(agentDto);
    }

    @Override
    public int insertAgent(AgentDto agentDto) throws Exception {
        agentDto.setPasswordStr(agentDto.getPassword());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        agentDto.setPassword(passwordEncoder.encode(agentDto.getPassword()));

        return agentMapper.insertAgent(agentDto);
    }

    @Override
    public int updateAgent(AgentDto agentDto) throws Exception {
        agentDto.setPasswordStr(agentDto.getPassword());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        agentDto.setPassword(passwordEncoder.encode(agentDto.getPassword()));

        return agentMapper.updateAgent(agentDto);
    }

    @Override
    public int deleteAgent(AgentDto agentDto) throws Exception {
        return agentMapper.deleteAgent(agentDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAgentAddPoint(AgentDto agentDto) throws Exception {
        try {
            int result = -1;

            //총 포인트 증가
            result = agentMapper.updateAgentAddPoint(agentDto);

            //포인트 내역 생성
            if(result > 0){
                agentMapper.insertAddAgentPoint(agentDto);
                //본사 포인트 처리 내역
                if(agentDto.getAgentUpCode() != null) {
                    if (agentDto.getAgentUpCode().equals(1)) {
                        agentMapper.insertAddTopAgentPoint(agentDto);
                    }
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAgentAddTicket(AgentDto agentDto) throws Exception {
        try {
            int result = -1;

            //총 티켓 증가
            result = agentMapper.updateAgentAddTicket(agentDto);

            //포인트 내역 생성
            if(result > 0){
                agentMapper.insertAddAgentTicket(agentDto);
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAgentAddTicket2(AgentDto agentDto) throws Exception {
        try {
            int result = -1;

            //총 티켓 증가
            result = agentMapper.updateAgentAddTicket2(agentDto);

            //포인트 내역 생성
            if(result > 0){
                agentMapper.insertAddAgentTicket2(agentDto);
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAgentMinusPoint(AgentDto agentDto) throws Exception {
        try {
            int result = -1;

            //총 포인트 차감
            result = agentMapper.updateAgentMinusPoint(agentDto);

            //포인트 차감 내역 생성
            if(result > 0){
                agentMapper.insertMinusAgentPoint(agentDto);
                //본사 포인트 처리 내역
                if(agentDto.getAgentUpCode() != null){
                    if(agentDto.getAgentUpCode().equals(1)) {
                        agentMapper.insertMinusTopAgentPoint(agentDto);
                    }
                }
            }

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAgentMinusTicket(AgentDto agentDto) throws Exception {
        try {
            int result = -1;

            //총 티켓 차감
            result = agentMapper.updateAgentMinusTicket(agentDto);

            //티켓 차감 내역 생성
            if(result > 0){
                agentMapper.insertMinusAgentTicket(agentDto);
            }

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateAgentMinusTicket2(AgentDto agentDto) throws Exception {
        try {
            int result = -1;

            //총 티켓 차감
            result = agentMapper.updateAgentMinusTicket2(agentDto);

            //티켓 차감 내역 생성
            if(result > 0){
                agentMapper.insertMinusAgentTicket2(agentDto);
            }

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<PointDto> selectAgentPointHistory(AgentDto agentDto) throws Exception {
        return agentMapper.selectAgentPointHistory(agentDto);
    }

    @Override
    public List<PointDto> selectAgentTicketHistory(AgentDto agentDto) throws Exception {
        return agentMapper.selectAgentTicketHistory(agentDto);
    }

    @Override
    public List<PointDto> selectAgentTicketHistory2(AgentDto agentDto) throws Exception {
        return agentMapper.selectAgentTicketHistory2(agentDto);
    }

    @Override
    public AgentDto selectAgentInfo(AgentDto agentDto) throws Exception {
        return agentMapper.selectAgentInfo(agentDto);
    }

    @Override
    public List<AgentDto> selectAgentTotalListAsAG() throws Exception {
        return agentMapper.selectAgentTotalListAsAG();
    }

    @Override
    public List<AgentDto> selectAgentPointList(AgentDto agentDto) throws Exception {
        int userPointTotalCount = agentMapper.selectAgentPointListTotalCount(agentDto);

        PaginationInfo paginationInfo = new PaginationInfo(agentDto);
        paginationInfo.setTotalRecordCount(userPointTotalCount);
        agentDto.setPaginationInfo(paginationInfo);

        return agentMapper.selectAgentPointList(agentDto);
    }

}
