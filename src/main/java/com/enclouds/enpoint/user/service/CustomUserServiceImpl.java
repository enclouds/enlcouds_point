package com.enclouds.enpoint.user.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
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
public class CustomUserServiceImpl implements CustomUserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AgentService agentService;

    @Override
    public List<UserDto> selectCustomUserList(UserDto userDto) throws Exception {
        int userTotalCount = userMapper.selectCustomUserListTotalCount(userDto);

        PaginationInfo paginationInfo = new PaginationInfo(userDto);
        paginationInfo.setTotalRecordCount(userTotalCount);
        userDto.setPaginationInfo(paginationInfo);

        return userMapper.selectCustomUserList(userDto);
    }

    @Override
    public List<PointDto> selectUserPointList(PointDto pointDto) throws Exception {
        int userPointTotalCount = userMapper.selectUserPointListTotalCount(pointDto);

        PaginationInfo paginationInfo = new PaginationInfo(pointDto);
        paginationInfo.setTotalRecordCount(userPointTotalCount);
        pointDto.setPaginationInfo(paginationInfo);

        return userMapper.selectUserPointList(pointDto);
    }

    @Override
    public PointDto selectUserPointTotal(PointDto pointDto) throws Exception {
        return userMapper.selectUserPointTotal(pointDto);
    }

    @Override
    public int selectDuplUser(UserDto userDto) throws Exception {
        return userMapper.selectDuplUser(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int insertUser(UserDto userDto) throws Exception {
        try {
            int result = -1;

            //포인트 처음 제공시
            if(!userDto.getPoint().equals("0")){
                userDto.setAddPoint(userDto.getPoint());
                result = userMapper.insertAddPoint(userDto);

                if(result > 0){
                    //카카오톡 전송
                    KakaoDto kakaoDto = new KakaoDto();
                    kakaoDto.setTemplateId("KA01TP221011062400156k4kpTZGoW5f");
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setAddPoint(userDto.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setTotalPoint(userDto.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(userDto.getAgentName());

                    KakaoExampleController kakaoExampleController = new KakaoExampleController();
                    kakaoExampleController.sendOneAta(kakaoDto);
                }

                //해당 가맹점 포인트 차감
                if(result > 0){
                    AgentDto agentDto = new AgentDto();
                    agentDto.setAgentCode(userDto.getAgentCode());
                    agentDto.setMinusPoint(userDto.getPoint());

                    agentService.updateAgentMinusPoint(agentDto);
                }
            }

            result = userMapper.insertUser(userDto);

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUser(UserDto userDto) throws Exception {
        return userMapper.updateUser(userDto);
    }

    @Override
    public int deleteUser(UserDto userDto) throws Exception {
        return userMapper.deleteUser(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddPoint(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto prePointDto = userMapper.getTotalPoint(userDto);
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(userDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getPointInt() < Integer.parseInt(userDto.getAddPoint())){
                result = -2;
                return  result;
            }

            //총 포인트 증가
            result = userMapper.updateUserAddPoint(userDto);

            //포인트 내역 생성
            if(result > 0){
                userDto.setPrivateDefPoint(prePointDto.getPointInt());
                result = userMapper.insertAddPoint(userDto);

                if(result > 0){
                    //카카오톡 전송
                    PointDto pointDto = userMapper.getTotalPoint(userDto);

                    KakaoDto kakaoDto = new KakaoDto();
                    kakaoDto.setTemplateId("KA01TP230727025205254EyRZK4vF8Qi");
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setAddPoint(userDto.getAddPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setTotalPoint(pointDto.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(pointDto.getAgentName());

                    KakaoExampleController kakaoExampleController = new KakaoExampleController();
                    kakaoExampleController.sendOneAta(kakaoDto);
                }

                //해당 가맹점 포인트 차감
                if(result > 0){
                    AgentDto agentDto1 = new AgentDto();
                    agentDto1.setAgentCode(userDto.getAgentCode());
                    agentDto1.setMinusPoint(userDto.getAddPoint());

                    agentService.updateAgentMinusPoint(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserAddCouponPoint(UserDto userDto) throws Exception {
        return userMapper.updateUserAddCouponPoint(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddTicket(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto preTicketDto = userMapper.getTotalTicket(userDto);
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(userDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getTicketInt() < Integer.parseInt(userDto.getAddTicket())){
                result = -2;
                return  result;
            }

            //총 티켓 증가
            result = userMapper.updateUserAddTicket(userDto);

            //티켓 내역 생성
            if(result > 0){
                userDto.setDefTicket(agentInfo.getTicketInt());
                userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
                result = userMapper.insertAddTicket(userDto);

                /*if(result > 0){
                    //카카오톡 전송
                    PointDto pointDto = userMapper.getTotalPoint(userDto);

                    KakaoDto kakaoDto = new KakaoDto();
                    kakaoDto.setTemplateId("KA01TP221011062400156k4kpTZGoW5f");
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setAddPoint(userDto.getAddPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setTotalPoint(pointDto.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(pointDto.getAgentName());

                    KakaoExampleController kakaoExampleController = new KakaoExampleController();
                    kakaoExampleController.sendOneAta(kakaoDto);
                }*/

                //해당 가맹점 티켓 차감
                if(result > 0){
                    AgentDto agentDto1 = new AgentDto();
                    agentDto1.setAgentCode(userDto.getAgentCode());
                    agentDto1.setMinusTicket(userDto.getAddTicket());

                    agentService.updateAgentMinusTicket(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddTicket2(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto preTicketDto = userMapper.getTotalTicket2(userDto);
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(userDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getTicketInt2() < Integer.parseInt(userDto.getAddTicket())){
                result = -2;
                return  result;
            }

            //총 티켓 증가
            result = userMapper.updateUserAddTicket2(userDto);

            //티켓 내역 생성
            if(result > 0){
                userDto.setDefTicket(agentInfo.getTicketInt2());
                userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
                result = userMapper.insertAddTicket2(userDto);

                /*if(result > 0){
                    //카카오톡 전송
                    PointDto pointDto = userMapper.getTotalPoint(userDto);

                    KakaoDto kakaoDto = new KakaoDto();
                    kakaoDto.setTemplateId("KA01TP221011062400156k4kpTZGoW5f");
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setAddPoint(userDto.getAddPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setTotalPoint(pointDto.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(pointDto.getAgentName());

                    KakaoExampleController kakaoExampleController = new KakaoExampleController();
                    kakaoExampleController.sendOneAta(kakaoDto);
                }*/

                //해당 가맹점 티켓 차감
                if(result > 0){
                    AgentDto agentDto1 = new AgentDto();
                    agentDto1.setAgentCode(userDto.getAgentCode());
                    agentDto1.setMinusTicket(userDto.getAddTicket());

                    agentService.updateAgentMinusTicket2(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserAddRankPoint(UserDto userDto) throws Exception {
        return userMapper.updateUserAddRankPoint(userDto);
    }

    @Override
    public int useTicket(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicket(userDto);

        return userMapper.useTicket(userDto);
    }

    @Override
    public int useTicket2(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket2(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicket2(userDto);

        return userMapper.useTicket2(userDto);
    }

    @Override
    public int updateUserMinusRankPoint(UserDto userDto) throws Exception {
        return userMapper.updateUserMinusRankPoint(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserMinusPoint(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto pointDto = userMapper.getTotalPoint(userDto);

            if(pointDto.getPointInt() < Integer.parseInt(userDto.getMinusPoint())){
                result = -2;
                return  result;
            }

            //총 포인트 차감
            result = userMapper.updateUserMinusPoint(userDto);

            //포인트 내역 생성
            if(result > 0){
                userDto.setPrivateDefPoint(pointDto.getPointInt());
                result = userMapper.insertMinusPoint(userDto);

                if(result > 0){

                    PointDto pointDto2 = userMapper.getTotalPoint(userDto);

                    KakaoDto kakaoDto = new KakaoDto();
                    kakaoDto.setTemplateId("KA01TP230727025242092bgsDIX9xp9W");
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setMinusPoint(userDto.getMinusPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setTotalPoint(pointDto2.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(pointDto2.getAgentName());

                    KakaoExampleController kakaoExampleController = new KakaoExampleController();
                    kakaoExampleController.sendOneAta(kakaoDto);
                }

                //해당 가맹점 포인트 적립
                if(result > 0){
                    AgentDto agentDto = new AgentDto();
                    agentDto.setAgentCode(userDto.getAgentCode());
                    agentDto.setAddPoint(userDto.getMinusPoint());

                    agentService.updateAgentAddPoint(agentDto);
                }
            }

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserMinusTicket(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicketAsCnt(userDto);

        return userMapper.useTicketAsCnt(userDto);
    }

    @Override
    public int updateUserMinusTicket2(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket2(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicketAsCnt2(userDto);

        return userMapper.useTicketAsCnt2(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserMinusCouponPoint(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto pointDto = userMapper.getTotalCouponPoint(userDto);

            if(pointDto.getPointInt() < Integer.parseInt(userDto.getMinusCouponPoint())){
                result = -2;
                return  result;
            }

            //총 포인트 차감
            result = userMapper.updateUserMinusCouponPoint(userDto);

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserHumanAjax(UserDto userDto) throws Exception {
        try {
            int result = -1;
            PointDto pointDto = userMapper.getTotalPoint(userDto);

            //총 포인트 차감
            userDto.setMinusPoint(String.valueOf(pointDto.getPointInt()));
            result = userMapper.updateUserMinusPoint(userDto);

            //포인트 내역 생성
            if(result > 0){
                userDto.setPrivateDefPoint(pointDto.getPointInt());
                result = userMapper.insertHumanPoint(userDto);

                if(result > 0){

                    PointDto pointDto2 = userMapper.getTotalPoint(userDto);

                    KakaoDto kakaoDto = new KakaoDto();
                    kakaoDto.setTemplateId("KA01TP221206060900681elE0OAyzmRT");
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setMinusPoint(userDto.getMinusPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(pointDto2.getAgentName());

                    KakaoExampleController kakaoExampleController = new KakaoExampleController();
                    kakaoExampleController.sendOneAta(kakaoDto);
                }

                //해당 가맹점 포인트 적립
                if(result > 0){
                    AgentDto agentDto = new AgentDto();
                    agentDto.setAgentCode(userDto.getAgentCode());
                    agentDto.setAddPoint(userDto.getMinusPoint());

                    agentService.updateAgentAddPoint(agentDto);
                }
            }

            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public int updateUserRankDefAjax(UserDto userDto) throws Exception {
        return userMapper.updateUserRankDef(userDto);
    }

    @Override
    public List<PointDto> selectPointHistory(UserDto userDto) throws Exception {
        return userMapper.selectPointHistory(userDto);
    }

    @Override
    public List<UserDto> selectUserRankList(UserDto userDto) throws Exception {
        return userMapper.selectUserRankList(userDto);
    }

    @Override
    public void updateWeekRankPoint() throws Exception {
        //전체 누적 승점 증가
        userMapper.updateWeekRankPoint();
        //전체 주간 승점 0으로 변경
        userMapper.updateRankPointToZero();
    }

    @Override
    public int updateAllUserWeekRankDefAjax() throws Exception {
        return userMapper.updateAllUserWeekRankDefAjax();
    }

    @Override
    public int updateAllUserRankDefAjax() throws Exception {
        return userMapper.updateAllUserRankDefAjax();
    }

    @Override
    public int updateAllUserRankMoveAjax() throws Exception {
        userMapper.updateAllUserRankMove();
        userMapper.updateAllUserRankMoveDef();

        return 1;
    }

    @Override
    public UserDto selectCustomUserInfo(UserDto userDto) throws Exception {
        return userMapper.selectCustomUserInfo(userDto);
    }

    @Override
    public List<UserDto> selectUserTicketRankList(UserDto userDto) throws Exception {
        return userMapper.selectUserTicketRankList(userDto);
    }

    @Override
    public List<PointDto> selectTicketHistory(UserDto userDto) throws Exception {
        return userMapper.selectTicketHistory(userDto);
    }

    @Override
    public List<PointDto> selectTicketHistory2(UserDto userDto) throws Exception {
        return userMapper.selectTicketHistory2(userDto);
    }

    @Override
    public int updateUserAddSumRankPoint(UserDto userDto) throws Exception {
        return userMapper.updateUserAddSumRankPoint(userDto);
    }

    @Override
    public int updateUserMinusSumRank(UserDto userDto) throws Exception {
        return userMapper.updateUserMinusSumRank(userDto);
    }
}
