package com.enclouds.enpoint.jackpot.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.jackpot.dto.JackpotDto;
import com.enclouds.enpoint.jackpot.mapper.JackpotMapper;
import com.enclouds.enpoint.nurigo.KakaoDto;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.mapper.UserMapper;
import net.nurigo.sdk.KakaoExampleController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JackpotServiceImpl implements JackpotService{

    @Autowired
    private JackpotMapper jackpotMapper;

    @Autowired
    private AgentService agentService;

    @Override
    public List<JackpotDto> selectJackpotList(JackpotDto jackpotDto) throws Exception {
        int jackpotTotalCount = jackpotMapper.selectJackpotListTotalCount(jackpotDto);

        PaginationInfo paginationInfo = new PaginationInfo(jackpotDto);
        paginationInfo.setTotalRecordCount(jackpotTotalCount);
        jackpotDto.setPaginationInfo(paginationInfo);

        return jackpotMapper.selectJackpotList(jackpotDto);
    }

    @Override
    public List<JackpotDto> selectJackpotListTotal(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.selectJackpotListTotal(jackpotDto);
    }

    @Override
    public List<JackpotDto> selectJackpotNotiList(JackpotDto jackpotDto) throws Exception {
        int jackpotNotiTotalCount = jackpotMapper.selectJackpotNotiListTotalCount(jackpotDto);

        PaginationInfo paginationInfo = new PaginationInfo(jackpotDto);
        paginationInfo.setTotalRecordCount(jackpotNotiTotalCount);
        jackpotDto.setPaginationInfo(paginationInfo);

        return jackpotMapper.selectJackpotNotiList(jackpotDto);
    }

    @Override
    public int insertJackpot(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.insertJackpot(jackpotDto);
    }

    @Override
    public int updateJackpot(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.updateJackpot(jackpotDto);
    }

    @Override
    public int deleteJackpot(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.deleteJackpot(jackpotDto);
    }

    @Override
    public JackpotDto selectJackPotInfo(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.selectJackPotInfo(jackpotDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int jackpotAddPointAjax(JackpotDto jackpotDto) throws Exception {
        try {
            int result = -1;

            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(jackpotDto.getRegAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getPointInt() < 10000){
                result = -2;
                return  result;
            }

            //잭팟 포인트 증가
            result = jackpotMapper.updateJackpotPoint(jackpotDto);

            //잭팟 적립 내역 생성
            if(result > 0){
                result = jackpotMapper.insertAddJackpot(jackpotDto);

                //해당 가맹점 포인트 차감
                if(result > 0){
                    AgentDto agentDto1 = new AgentDto();
                    agentDto1.setAgentCode(jackpotDto.getRegAgentCode());
                    agentDto1.setMinusPoint("10000");

                    agentService.updateAgentMinusPoint(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int insertNotiAjax(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.insertNotiAjax(jackpotDto);
    }

    @Override
    public int updateNotiAjax(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.updateNotiAjax(jackpotDto);
    }

    @Override
    public int deleteNotiAjax(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.deleteNotiAjax(jackpotDto);
    }

    @Override
    public List<JackpotDto> selectJackpotNotiListTotal(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.selectJackpotNotiListTotal(jackpotDto);
    }

    @Override
    public JackpotDto selectJackPotInfoTotal(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.selectJackPotInfoTotal(jackpotDto);
    }

    @Override
    public int addJackpotAjax(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.addJackpotAjax(jackpotDto);
    }

    @Override
    public int minusJackpotAjax(JackpotDto jackpotDto) throws Exception {
        return jackpotMapper.minusJackpotAjax(jackpotDto);
    }

    @Override
    public List<JackpotDto> selectJackpotHistoryList(JackpotDto jackpotDto) throws Exception {
        int jackpotHistoryTotalCount = jackpotMapper.selectJackpotHistoryListTotalCount(jackpotDto);

        PaginationInfo paginationInfo = new PaginationInfo(jackpotDto);
        paginationInfo.setTotalRecordCount(jackpotHistoryTotalCount);
        jackpotDto.setPaginationInfo(paginationInfo);

        return jackpotMapper.selectJackpotHistoryList(jackpotDto);
    }

}
