package com.enclouds.enpoint.api.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.api.dto.*;
import com.enclouds.enpoint.api.mapper.ApiMapper;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ApiServiceImpl implements ApiService{

    @Autowired
    private ApiMapper apiMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AgentService agentService;

    @Override
    public ApiUserRtnDto selectUserInfo(ApiDto apiDto) throws Exception {
        return apiMapper.selectUserInfo(apiDto);
    }

    @Override
    public List<ApiPointHistoryDto> getPointHistory(ApiDto apiDto) throws Exception {
        return apiMapper.getPointHistory(apiDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addPoint(ApiDto apiDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiDto.getPhoneNum());
        userDto.setAddPoint(apiDto.getAddPoint());

        PointDto prePointDto = userMapper.getTotalPoint(userDto);

        int result = userMapper.updateUserAddPoint(userDto);
        if(result > 0){
            userDto.setPrivateDefPoint(prePointDto.getPointInt());
            userDto.setDefPoint(0);
            userDto.setAgentCode(16);
            result = userMapper.insertAddPoint(userDto);
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int minusPoint(ApiDto apiDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiDto.getPhoneNum());
        userDto.setMinusPoint(apiDto.getMinusPoint());

        PointDto pointDto = userMapper.getTotalPoint(userDto);

        int result = userMapper.updateUserMinusPoint(userDto);
        if(result > 0){
            userDto.setPrivateDefPoint(pointDto.getPointInt());
            userDto.setDefPoint(0);
            userDto.setAgentCode(16);
            result = userMapper.insertMinusPoint(userDto);
        }

        return result;
    }

    @Override
    public List<ApiTicketHistoryDto> getTicketHistory(ApiDto apiDto) throws Exception {
        List<ApiTicketHistoryDto> ticketHistoryDtoList = new ArrayList<>();

        if(apiDto.getTicketGbn().equals("1")){
            ticketHistoryDtoList = apiMapper.getTicketHistory1(apiDto);
        }else if(apiDto.getTicketGbn().equals("2")) {
            ticketHistoryDtoList = apiMapper.getTicketHistory2(apiDto);
        }

        return ticketHistoryDtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addTicket(ApiDto apiDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiDto.getPhoneNum());
        userDto.setAddTicket(apiDto.getAddTicket());

        PointDto preTicketDto = userMapper.getTotalTicket(userDto);
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(16);
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userDto.setAgentCode(16);

        int result = 0;

        if(apiDto.getTicketGbn().equals("1")){
            result = userMapper.insertAddTicket(userDto);

            if(result > 0){
                result = userMapper.updateUserAddTicket(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("2")){
            result = userMapper.insertAddTicket2(userDto);

            if(result > 0){
                result = userMapper.updateUserAddTicket2(userDto);
            }
        }

        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int minusTicket(ApiDto apiDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiDto.getPhoneNum());
        userDto.setMinusTicket(apiDto.getMinusTicket());
        userDto.setAgentCode(16);

        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(16);
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket(userDto);
        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

        int result = 0;
        if(apiDto.getTicketGbn().equals("1")){
            userMapper.insertMinusTicketAsCnt(userDto);
            result = userMapper.useTicketAsCnt(userDto);
        }else if(apiDto.getTicketGbn().equals("2")){
            userMapper.insertMinusTicketAsCnt2(userDto);
            result = userMapper.useTicketAsCnt2(userDto);
        }

        return result;
    }

    @Override
    public int addCoupon(ApiDto apiDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiDto.getPhoneNum());
        userDto.setAddCouponPoint(apiDto.getAddCoupon());

        return userMapper.updateUserAddCouponPoint(userDto);
    }

    @Override
    public int addWinPoint(ApiDto apiDto) throws Exception {
        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiDto.getPhoneNum());
        userDto.setAddRankPoint(apiDto.getAddWinPoint());

        return userMapper.updateUserAddRankPoint(userDto);
    }

    @Override
    public List<ApiRankDto> getRankList(ApiDto apiDto) throws Exception {
        List<ApiRankDto> apiRankListDto = apiMapper.getRankList(apiDto);

        return apiRankListDto;
    }

    @Override
    public List<ApiTicketRankDto> getTicketRankList(ApiDto apiDto) throws Exception {
        List<ApiTicketRankDto> apiTicketRankDto = apiMapper.getTicketRankList(apiDto);

        return apiTicketRankDto;
    }

}
