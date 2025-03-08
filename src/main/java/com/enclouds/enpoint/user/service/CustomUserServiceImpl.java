package com.enclouds.enpoint.user.service;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.agent.mapper.AgentMapper;
import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.nurigo.KakaoDto;
import com.enclouds.enpoint.user.dto.*;
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

    @Autowired
    private AgentMapper agentMapper;

    @Override
    public List<UserDto> selectCustomUserList(UserDto userDto) throws Exception {
        int userTotalCount = userMapper.selectCustomUserListTotalCount(userDto);

        PaginationInfo paginationInfo = new PaginationInfo(userDto);
        paginationInfo.setTotalRecordCount(userTotalCount);
        userDto.setPaginationInfo(paginationInfo);

        return userMapper.selectCustomUserList(userDto);
    }

    @Override
    public List<UserDto> selectCustomUserListByVisit(UserDto userDto) throws Exception {
        int userTotalCount = userMapper.selectCustomUserListByVisitTotalCount(userDto);

        PaginationInfo paginationInfo = new PaginationInfo(userDto);
        paginationInfo.setTotalRecordCount(userTotalCount);
        userDto.setPaginationInfo(paginationInfo);

        return userMapper.selectCustomUserListByVisit(userDto);
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
    public List<CouponDto> selectUserCouponList(CouponDto couponDto) throws Exception {
        int userCouponTotalCount = userMapper.selectUserCouponListTotalCount(couponDto);

        PaginationInfo paginationInfo = new PaginationInfo(couponDto);
        paginationInfo.setTotalRecordCount(userCouponTotalCount);
        couponDto.setPaginationInfo(paginationInfo);

        return userMapper.selectUserCouponList(couponDto);
    }

    @Override
    public CouponDto selectUserCouponTotal(CouponDto couponDto) throws Exception {
        return userMapper.selectUserCouponTotal(couponDto);
    }

    @Override
    public int selectDuplUser(UserDto userDto) throws Exception {
        return userMapper.selectDuplUser(userDto);
    }

    @Override
    public int selectDuplUser2(UserDto userDto) throws Exception {
        return userMapper.selectDuplUser2(userDto);
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

            //신규가입시 온라인 티켓 지급
            /*if(result > 0){
                PointDto preTicketDto = userMapper.getTotalTicket5(userDto);
                AgentDto agentDto = new AgentDto();
                agentDto.setAgentCode(userDto.getAgentCode());
                AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

                userDto.setAddTicket("1");
                userDto.setDefTicket(agentInfo.getTicketInt5());
                userDto.setPrivateDefTicket(preTicketDto.getTicketInt());

                result = userMapper.insertAddTicket5(userDto);

                //해당 가맹점 티켓 차감
                if(result > 0){
                    AgentDto agentDto1 = new AgentDto();
                    agentDto1.setAgentCode(userDto.getAgentCode());
                    agentDto1.setMinusTicket(userDto.getAddTicket());

                    agentService.updateAgentMinusTicket5(agentDto1);
                }

            }*/

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
                    kakaoDto.setTemplateId("KA01TP241219020324371IRqxVxK9p6J");
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
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddCouponPoint(UserDto userDto) throws Exception {
        try {
            int result = userMapper.updateUserAddCouponPoint(userDto);

            /**
             * 쿠폰 적립 후 히스토리 생성
             */
            if (result > 0) {
                userDto.setCouponGbn("ADD");
                userDto.setHistoryCouponCnt(userDto.getAddCouponPoint());
                result = userMapper.insertCouponHistory(userDto);
            }

            return result;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
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
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddTicket3(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto preTicketDto = userMapper.getTotalTicket3(userDto);
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(userDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getTicketInt3() < Integer.parseInt(userDto.getAddTicket())){
                result = -2;
                return  result;
            }

            //총 티켓 증가
            result = userMapper.updateUserAddTicket3(userDto);

            //티켓 내역 생성
            if(result > 0){
                userDto.setDefTicket(agentInfo.getTicketInt3());
                userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
                result = userMapper.insertAddTicket3(userDto);

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

                    agentService.updateAgentMinusTicket3(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddTicket4(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto preTicketDto = userMapper.getTotalTicket4(userDto);
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(userDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getTicketInt4() < Integer.parseInt(userDto.getAddTicket())){
                result = -2;
                return  result;
            }

            //총 티켓 증가
            result = userMapper.updateUserAddTicket4(userDto);

            //티켓 내역 생성
            if(result > 0){
                userDto.setDefTicket(agentInfo.getTicketInt4());
                userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
                result = userMapper.insertAddTicket4(userDto);

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

                    agentService.updateAgentMinusTicket4(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserAddTicket5(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto preTicketDto = userMapper.getTotalTicket5(userDto);
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(userDto.getAgentCode());
            AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

            if(agentInfo.getTicketInt5() < Integer.parseInt(userDto.getAddTicket())){
                result = -2;
                return  result;
            }

            //총 티켓 증가
            result = userMapper.updateUserAddTicket5(userDto);

            //티켓 내역 생성
            if(result > 0){
                userDto.setDefTicket(agentInfo.getTicketInt5());
                userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
                result = userMapper.insertAddTicket5(userDto);

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

                    agentService.updateAgentMinusTicket5(agentDto1);
                }
            }
            return result;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 승점 적립
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    @Override
    public int updateUserAddRankPoint(UserDto userDto) throws Exception {
        RankDto rankDto = new RankDto();
        rankDto.setAgentCode(userDto.getAgentCode());
        rankDto.setRankGbn("ADD");
        rankDto.setRank(Integer.parseInt(userDto.getAddRankPoint()));
        rankDto.setPhoneNum(userDto.getPhoneNum());

        RankDto preRankDto = userMapper.getTotalRank(userDto);
        rankDto.setPrivateDefRank(preRankDto.getPrivateDefRank());

        userMapper.insertAddRankPointHist(rankDto);

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
    public int useTicket3(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket3(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicket3(userDto);

        return userMapper.useTicket3(userDto);
    }

    /**
     * 승점 차감
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    @Override
    public int updateUserMinusRankPoint(UserDto userDto) throws Exception {
        RankDto rankDto = new RankDto();
        rankDto.setAgentCode(userDto.getAgentCode());
        rankDto.setRankGbn("MINUS");
        rankDto.setRank(Integer.parseInt(userDto.getMinusRankPoint()));
        rankDto.setPhoneNum(userDto.getPhoneNum());

        RankDto preRankDto = userMapper.getTotalRank(userDto);
        rankDto.setPrivateDefRank(preRankDto.getPrivateDefRank());

        userMapper.insertAddRankPointHist(rankDto);

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
                    kakaoDto.setTemplateId("KA01TP241219020224105xGE9ug9SxpI");
                    kakaoDto.setNickName(pointDto2.getNickName());
                    kakaoDto.setRcvNum(userDto.getPhoneNum());
                    kakaoDto.setCouponPoint(String.valueOf(pointDto2.getCouponPoint()));
                    kakaoDto.setTicket1(pointDto2.getTicket1());
                    kakaoDto.setTicket2(pointDto2.getTicket2());
                    kakaoDto.setTicket3(pointDto2.getTicket3());
                    kakaoDto.setTicket4(pointDto2.getTicket4());
                    kakaoDto.setTicket5(pointDto2.getTicket5());
                    kakaoDto.setMinusPoint(userDto.getMinusPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setTotalPoint(pointDto2.getPoint().replaceAll("\\B(?=(\\d{3})+(?!\\d))", ","));
                    kakaoDto.setStoreNm(pointDto2.getAgentName());
                    kakaoDto.setAgentTel(pointDto2.getAgentTel());

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

        userDto.setDefTicket(agentInfo.getTicketInt2());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicketAsCnt2(userDto);

        return userMapper.useTicketAsCnt2(userDto);
    }

    @Override
    public int updateUserMinusTicket3(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket3(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt3());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicketAsCnt3(userDto);

        return userMapper.useTicketAsCnt3(userDto);
    }

    @Override
    public int updateUserMinusTicket4(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket4(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt4());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicketAsCnt4(userDto);

        return userMapper.useTicketAsCnt4(userDto);
    }

    @Override
    public int updateUserMinusTicket5(UserDto userDto) throws Exception {
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(userDto.getAgentCode());
        AgentDto agentInfo = agentService.selectAgentInfo(agentDto);

        PointDto preTicketDto = userMapper.getTotalTicket5(userDto);

        userDto.setDefTicket(agentInfo.getTicketInt5());
        userDto.setPrivateDefTicket(preTicketDto.getTicketInt());
        userMapper.insertMinusTicketAsCnt5(userDto);

        int result = userMapper.useTicketAsCnt5(userDto);

        if(result > 0){
            //KLPT 티켓 차감은 가맹점으로 환불 처리
            agentDto.setAddTicket(userDto.getMinusTicket());
            result = agentService.updateAgentAddTicket5(agentDto);
        }
        return result;
    }

    @Override
    public int updateTicket1ResetAjax(UserDto userDto) throws Exception {
        return userMapper.updateTicket1ResetAjax(userDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateUserMinusCouponPoint(UserDto userDto) throws Exception {
        try {
            int result = -1;

            PointDto pointDto = userMapper.getTotalCouponPoint(userDto);

            if(pointDto.getCouponPoint() < userDto.getMinusCouponPoint()){
                result = -2;
                return  result;
            }

            //총 포인트 차감
            result = userMapper.updateUserMinusCouponPoint(userDto);

            if(result > 0){
                userDto.setCouponGbn("MINUS");
                userDto.setHistoryCouponCnt(userDto.getMinusCouponPoint());
                result = userMapper.insertCouponHistory(userDto);
            }

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
        int userRankTotalCount = userMapper.selectUserRankListListTotalCount(userDto);

        PaginationInfo paginationInfo = new PaginationInfo(userDto);
        paginationInfo.setTotalRecordCount(userRankTotalCount);
        userDto.setPaginationInfo(paginationInfo);

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
    public int updateAllUserWeekTicketDefAjax() throws Exception {
        return userMapper.updateAllUserWeekTicketDefAjax();
    }

    @Override
    public int updateAllUserMonthTicketDefAjax() throws Exception {
        return userMapper.updateAllUserMonthTicketDefAjax();
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
        int userTicketTotalCount = userMapper.selectUserTicketRankListTotalCount(userDto);

        PaginationInfo paginationInfo = new PaginationInfo(userDto);
        paginationInfo.setTotalRecordCount(userTicketTotalCount);
        userDto.setPaginationInfo(paginationInfo);

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
    public List<PointDto> selectTicketHistory3(UserDto userDto) throws Exception {
        return userMapper.selectTicketHistory3(userDto);
    }

    @Override
    public List<PointDto> selectTicketHistory4(UserDto userDto) throws Exception {
        return userMapper.selectTicketHistory4(userDto);
    }

    @Override
    public List<PointDto> selectTicketHistory5(UserDto userDto) throws Exception {
        return userMapper.selectTicketHistory5(userDto);
    }

    @Override
    public List<CouponDto> selectCouponHistory(UserDto userDto) throws Exception {
        return userMapper.selectCouponHistory(userDto);
    }

    @Override
    public List<RankDto> selectRankHistory(UserDto userDto) throws Exception {
        return userMapper.selectRankHistory(userDto);
    }

    @Override
    public int updateUserAddSumRankPoint(UserDto userDto) throws Exception {
        return userMapper.updateUserAddSumRankPoint(userDto);
    }

    @Override
    public int updateUserMinusSumRank(UserDto userDto) throws Exception {
        return userMapper.updateUserMinusSumRank(userDto);
    }

    @Override
    public String selectPointSum() throws Exception{
        return userMapper.selectPointSum();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int ticketBuy(TicketBuyDto ticketBuyDto) throws Exception {
        int price = userMapper.selectPrice(ticketBuyDto);
        int totalAmt = price * ticketBuyDto.getBuyCnt();
        int result = 0;

        //보유포인트가 구매금액보다 작으면 구매 실패
        if(totalAmt > ticketBuyDto.getPointInt()){
            result = -2;
        }else {
            //포인트 차감
            AgentDto agentDto = new AgentDto();
            agentDto.setAgentCode(ticketBuyDto.getAgentCode());
            agentDto.setMinusPoint(String.valueOf(totalAmt));
            result = agentMapper.updateAgentMinusPoint(agentDto);
            result = agentMapper.insertMinusAgentPoint(agentDto);

            if(result > 0){
                //티켓 적립
                if(ticketBuyDto.getTicketGbn().equals("ticket")){
                    agentDto.setAddTicket(String.valueOf(ticketBuyDto.getBuyCnt()));
                    agentMapper.updateAgentAddTicket(agentDto);
                    agentMapper.insertAddAgentTicket(agentDto);
                } else if(ticketBuyDto.getTicketGbn().equals("ticket3")){
                    agentDto.setAddTicket(String.valueOf(ticketBuyDto.getBuyCnt()));
                    agentMapper.updateAgentAddTicket3(agentDto);
                    agentMapper.insertAddAgentTicket3(agentDto);
                } else if(ticketBuyDto.getTicketGbn().equals("ticket5")){
                    agentDto.setAddTicket(String.valueOf(ticketBuyDto.getBuyCnt()));
                    agentMapper.updateAgentAddTicket5(agentDto);
                    agentMapper.insertAddAgentTicket5(agentDto);
                }

                //내역 생성
                result = userMapper.insertBuyTicketHistory(ticketBuyDto);
            }
        }

        return result;
    }

    @Override
    public List<TicketBuyDto> selectTicketHistoryList(TicketBuyDto ticketBuyDto) throws Exception{
        int userTicketHistoryTotalCount = userMapper.selectTicketHistoryListTotalCount(ticketBuyDto);

        PaginationInfo paginationInfo = new PaginationInfo(ticketBuyDto);
        paginationInfo.setTotalRecordCount(userTicketHistoryTotalCount);
        ticketBuyDto.setPaginationInfo(paginationInfo);

        return userMapper.selectTicketHistoryList(ticketBuyDto);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int ticketSell(TicketBuyDto ticketBuyDto) throws Exception {
        ticketBuyDto.setTicketGbn(ticketBuyDto.getTicketSellGbn());
        int price = userMapper.selectPrice(ticketBuyDto);
        int totalAmt = price * ticketBuyDto.getSellCnt();
        int result = 0;

        //포인트 증가
        AgentDto agentDto = new AgentDto();
        agentDto.setAgentCode(ticketBuyDto.getAgentCode());
        agentDto.setAddPoint(String.valueOf(totalAmt));
        result = agentMapper.updateAgentAddPoint(agentDto);
        result = agentMapper.insertAddAgentPoint(agentDto);

        if(result > 0){
            //티켓 차감
            if(ticketBuyDto.getTicketSellGbn().equals("ticket5")){
                agentDto.setMinusTicket(String.valueOf(ticketBuyDto.getSellCnt()));
                agentMapper.updateAgentMinusTicket5(agentDto);
                agentMapper.insertMinusAgentTicket5(agentDto);
            }

            //내역 생성
            result = userMapper.insertSellTicketHistory(ticketBuyDto);
        }

        return result;
    }

}
