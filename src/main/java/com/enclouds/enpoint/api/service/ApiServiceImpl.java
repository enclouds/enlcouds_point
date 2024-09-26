package com.enclouds.enpoint.api.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.api.dto.*;
import com.enclouds.enpoint.api.mapper.ApiMapper;
import com.enclouds.enpoint.nurigo.KakaoDto;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.mapper.UserMapper;
import com.enclouds.enpoint.user.service.CustomUserService;
import com.enclouds.enpoint.user.service.UserService;
import net.nurigo.sdk.KakaoExampleController;
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

    @Autowired
    private CustomUserService customUserService;

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
    public int preAddPoint(ApiPreDto apiPreDto) throws Exception {
        int result = 0;

        UserDto userDto = new UserDto();
        userDto.setPhoneNum(apiPreDto.getPhoneNum());
        userDto.setAddPoint(apiPreDto.getAddPoint());
        userDto.setAgentCode(23);

        PointDto prePointDto = userMapper.getTotalPoint(userDto);

        if(prePointDto != null){
            //적립
            //부가세 10%제외
            double point = Double.valueOf(userDto.getAddPoint()) / 1.1;
            userDto.setAddPoint(String.format("%.0f", point));
            result = userMapper.updateUserAddPoint(userDto);

            if(result > 0){
                //적립 내역
                userDto.setPrivateDefPoint(prePointDto.getPointInt());
                userDto.setDefPoint(0);
                userDto.setTotalCouponPoint(prePointDto.getCouponPoint());
                result = userMapper.insertAddPoint(userDto);

                if(result > 0){
                    //외식쿠폰 적립
                    Double coupon = Double.parseDouble(userDto.getAddPoint()) / 10000;
                    userDto.setAddCouponPoint(coupon);
                    result = customUserService.updateUserAddCouponPoint(userDto);

                    if(result > 0){
                        //카카오톡 전송
                        PointDto pointDto = userMapper.getTotalPoint(userDto);

                        KakaoDto kakaoDto = new KakaoDto();
                        kakaoDto.setTemplateId("KA01TP240508062419040Z64NKB9aSZi");
                        kakaoDto.setNickName(pointDto.getNickName());
                        kakaoDto.setRcvNum(userDto.getPhoneNum());
                        kakaoDto.setCouponPoint(String.valueOf(pointDto.getCouponPoint()));
                        kakaoDto.setTicket1(pointDto.getTicket1());
                        kakaoDto.setTicket2(pointDto.getTicket2());
                        kakaoDto.setTicket3(pointDto.getTicket3());
                        kakaoDto.setTicket4(pointDto.getTicket4());
                        kakaoDto.setTicket5(pointDto.getTicket5());
                        kakaoDto.setAddPoint(userDto.getAddPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                        kakaoDto.setTotalPoint(pointDto.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                        kakaoDto.setStoreNm(pointDto.getAgentName());
                        kakaoDto.setAgentTel(pointDto.getAgentTel());

                        KakaoExampleController kakaoExampleController = new KakaoExampleController();
                        kakaoExampleController.sendOneAta(kakaoDto);
                    }
                }
            }
        }else {
            result = 999;
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

        if(pointDto.getPointInt() < Integer.parseInt(apiDto.getMinusPoint())){
            return -1;
        }else {
            int result = userMapper.updateUserMinusPoint(userDto);
            if (result > 0) {
                userDto.setPrivateDefPoint(pointDto.getPointInt());
                userDto.setDefPoint(0);
                userDto.setAgentCode(16);
                result = userMapper.insertMinusPoint(userDto);
            }

            return result;
        }
    }

    @Override
    public List<ApiTicketHistoryDto> getTicketHistory(ApiDto apiDto) throws Exception {
        List<ApiTicketHistoryDto> ticketHistoryDtoList = new ArrayList<>();

        if(apiDto.getTicketGbn().equals("1")){
            ticketHistoryDtoList = apiMapper.getTicketHistory1(apiDto);
        }else if(apiDto.getTicketGbn().equals("2")) {
            ticketHistoryDtoList = apiMapper.getTicketHistory2(apiDto);
        }else if(apiDto.getTicketGbn().equals("3")) {
            ticketHistoryDtoList = apiMapper.getTicketHistory3(apiDto);
        }else if(apiDto.getTicketGbn().equals("4")) {
            ticketHistoryDtoList = apiMapper.getTicketHistory4(apiDto);
        }else if(apiDto.getTicketGbn().equals("5")) {
            ticketHistoryDtoList = apiMapper.getTicketHistory5(apiDto);
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
        }else if(apiDto.getTicketGbn().equals("3")){
            result = userMapper.insertAddTicket3(userDto);

            if(result > 0){
                result = userMapper.updateUserAddTicket3(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("4")){
            result = userMapper.insertAddTicket4(userDto);

            if(result > 0){
                result = userMapper.updateUserAddTicket4(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("5")){
            result = userMapper.insertAddTicket5(userDto);

            if(result > 0){
                result = userMapper.updateUserAddTicket5(userDto);
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

        int result = 0;
        if(apiDto.getTicketGbn().equals("1")){
            PointDto preTicketDto = userMapper.getTotalTicket(userDto);
            userDto.setDefTicket(agentInfo.getTicketInt());
            userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

            if(preTicketDto.getTicketInt() < Integer.parseInt(apiDto.getMinusTicket())){
                return -1;
            }else {
                userMapper.insertMinusTicketAsCnt(userDto);
                result = userMapper.useTicketAsCnt(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("2")){

            PointDto preTicketDto = userMapper.getTotalTicket2(userDto);
            userDto.setDefTicket(agentInfo.getTicketInt());
            userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

            if(preTicketDto.getTicketInt() < Integer.parseInt(apiDto.getMinusTicket())){
                return -1;
            }else {
                userMapper.insertMinusTicketAsCnt2(userDto);
                result = userMapper.useTicketAsCnt2(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("3")){
            PointDto preTicketDto = userMapper.getTotalTicket3(userDto);
            userDto.setDefTicket(agentInfo.getTicketInt());
            userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

            if(preTicketDto.getTicketInt() < Integer.parseInt(apiDto.getMinusTicket())){
                return -1;
            }else {
                userMapper.insertMinusTicketAsCnt3(userDto);
                result = userMapper.useTicketAsCnt3(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("4")){
            PointDto preTicketDto = userMapper.getTotalTicket4(userDto);
            userDto.setDefTicket(agentInfo.getTicketInt());
            userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

            if(preTicketDto.getTicketInt() < Integer.parseInt(apiDto.getMinusTicket())){
                return -1;
            }else {
                userMapper.insertMinusTicketAsCnt4(userDto);
                result = userMapper.useTicketAsCnt4(userDto);
            }
        }else if(apiDto.getTicketGbn().equals("5")){
            PointDto preTicketDto = userMapper.getTotalTicket5(userDto);
            userDto.setDefTicket(agentInfo.getTicketInt());
            userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

            if(preTicketDto.getTicketInt() < Integer.parseInt(apiDto.getMinusTicket())){
                return -1;
            }else {
                userMapper.insertMinusTicketAsCnt5(userDto);
                result = userMapper.useTicketAsCnt5(userDto);
            }
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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int addJackpot(ApiDto apiDto) throws Exception {

        int result = apiMapper.addJackpot(apiDto);
        if(result > 0){
            result = apiMapper.insertAddJackpot(apiDto);
        }

        return result;
    }

    @Override
    public List<ApiBannerDto> getBannerList() throws Exception {
        return apiMapper.getBannerList();
    }

    @Override
    public List<ApiAgentDto> getAgentList() throws Exception {
        return apiMapper.getAgentList();
    }

    @Override
    public List<ApiTicketDto> getTicketList() throws Exception {
        return apiMapper.getTicketList();
    }

}
