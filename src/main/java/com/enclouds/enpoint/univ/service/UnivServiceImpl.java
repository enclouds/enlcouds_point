package com.enclouds.enpoint.univ.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.mapper.AgentMapper;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.univ.dto.UnivDto;
import com.enclouds.enpoint.univ.mapper.UnivMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UnivServiceImpl implements UnivService{

    @Autowired
    private UnivMapper univMapper;

    @Autowired
    private AgentService agentService;

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public List<UnivDto> selectUnivList(UnivDto univDto) throws Exception {
        int univTotalCount = univMapper.selectUnivListTotalCount(univDto);

        PaginationInfo paginationInfo = new PaginationInfo(univDto);
        paginationInfo.setTotalRecordCount(univTotalCount);
        univDto.setPaginationInfo(paginationInfo);

        return univMapper.selectUnivList(univDto);
    }

    @Override
    public List<UnivDto> selectUnivTotalList() throws Exception {
        return univMapper.selectUnivTotalList();
    }

    @Override
    public int insertUniv(UnivDto univDto) throws Exception {
        return univMapper.insertUniv(univDto);
    }

    @Override
    public int updateUniv(UnivDto univDto) throws Exception {
        return univMapper.updateUniv(univDto);
    }

    @Override
    public int deleteUniv(UnivDto univDto) throws Exception {
        return univMapper.deleteUniv(univDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUnivAddTicket(UnivDto univDto) throws Exception {
        try {
            int result = -1;

            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(univDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getTicket6() < univDto.getAddTicket()){
                result = -2;
                return  result;
            }

            result = univMapper.updateUnivAddTicket(univDto);

            //내역 생성
            if(result > 0){
                agentInfo.setAddTicket(String.valueOf(univDto.getAddTicket()));
                agentMapper.insertAddAgentTicket6(agentInfo);
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUnivMinusTicket(UnivDto univDto) throws Exception {
        try {
            int result = -1;

            //티켓 차감
            result = univMapper.updateUnivMinusTicket(univDto);

            //티켓 차감 내역 생성
            if(result > 0){
                AgentDto agentDto = new AgentDto();
                agentDto.setAgentCode(univDto.getAgentCode());
                agentDto.setMinusTicket(String.valueOf(univDto.getMinusTicket()));
                agentMapper.insertMinusAgentTicket6(agentDto);
            }

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

}
