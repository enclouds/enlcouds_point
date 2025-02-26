package com.enclouds.enpoint.user.mapper;

import com.enclouds.enpoint.user.dto.*;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

/**
 * 사용자 Mapper
 * @author Enclouds
 * @since 2020.12.11
 */

@Mapper
public interface UserMapper {

    /**
     * 사용자 정보 조회
     *
     * @param params
     * @return
     * @throws UsernameNotFoundException
     */
    UserDto selectUserInfo(UserDto params) throws UsernameNotFoundException;

    /**
     * 회원정보 조회
     *
     * @param userDto
     * @return
     * @throws Exception
     */
    List<UserDto> selectCustomUserList(UserDto userDto) throws Exception;

    List<UserDto> selectCustomUserListByVisit(UserDto userDto) throws Exception;

    UserDto selectCustomUserInfo(UserDto userDto) throws Exception;

    int selectCustomUserListTotalCount(UserDto userDto) throws Exception;

    int selectCustomUserListByVisitTotalCount(UserDto userDto) throws Exception;

    List<PointDto> selectUserPointList(PointDto pointDto) throws Exception;

    List<CouponDto> selectUserCouponList(CouponDto couponDto) throws Exception;

    PointDto selectUserPointTotal(PointDto pointDto) throws Exception;

    CouponDto selectUserCouponTotal(CouponDto couponDto) throws Exception;

    int selectUserPointListTotalCount(PointDto pointDto) throws Exception;

    int selectUserCouponListTotalCount(CouponDto couponDto) throws Exception;

    int selectDuplUser(UserDto userDto) throws Exception;

    int selectDuplUser2(UserDto userDto) throws Exception;

    int insertUser(UserDto userDto) throws Exception;

    int updateUser(UserDto userDto) throws Exception;

    int deleteUser(UserDto userDto) throws Exception;

    int updateUserAddPoint(UserDto userDto) throws Exception;

    int updateUserAddRankPoint(UserDto userDto) throws Exception;

    int insertAddRankPointHist(RankDto rankDto) throws Exception;

    int updateUserMinusRankPoint(UserDto userDto) throws Exception;

    int insertAddPoint(UserDto userDto) throws Exception;

    int insertAddTicket(UserDto userDto) throws Exception;

    int insertAddTicket2(UserDto userDto) throws Exception;

    int insertAddTicket3(UserDto userDto) throws Exception;

    int insertAddTicket4(UserDto userDto) throws Exception;

    int insertAddTicket5(UserDto userDto) throws Exception;

    int insertMinusTicket(UserDto userDto) throws Exception;

    int insertMinusTicket2(UserDto userDto) throws Exception;

    int insertMinusTicket3(UserDto userDto) throws Exception;

    int useTicket(UserDto userDto) throws Exception;

    int useTicket2(UserDto userDto) throws Exception;

    int useTicket3(UserDto userDto) throws Exception;

    int updateUserMinusPoint(UserDto userDto) throws Exception;

    int insertMinusPoint(UserDto userDto) throws Exception;

    int insertHumanPoint(UserDto userDto) throws Exception;

    int updateUserRankDef(UserDto userDto) throws Exception;

    List<PointDto> selectPointHistory(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory2(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory3(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory4(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory5(UserDto userDto) throws Exception;

    List<CouponDto> selectCouponHistory(UserDto userDto) throws Exception;

    List<RankDto> selectRankHistory(UserDto userDto) throws Exception;

    PointDto getTotalPoint(UserDto userDto) throws Exception;

    RankDto getTotalRank(UserDto userDto) throws Exception;

    PointDto getTotalTicket(UserDto userDto) throws Exception;

    PointDto getTotalTicket2(UserDto userDto) throws Exception;

    PointDto getTotalTicket3(UserDto userDto) throws Exception;

    PointDto getTotalTicket4(UserDto userDto) throws Exception;

    PointDto getTotalTicket5(UserDto userDto) throws Exception;

    PointDto getTotalCouponPoint(UserDto userDto) throws Exception;

    int selectUserRankListListTotalCount(UserDto userDto) throws Exception;

    List<UserDto> selectUserRankList(UserDto userDto) throws Exception;

    int selectUserTicketRankListTotalCount(UserDto userDto) throws Exception;

    List<UserDto> selectUserTicketRankList(UserDto userDto) throws Exception;

    int updateUserAddCouponPoint(UserDto userDto) throws Exception;

    int updateUserMinusCouponPoint(UserDto userDto) throws Exception;

    void updateWeekRankPoint() throws Exception;

    void updateRankPointToZero() throws Exception;

    int updateAllUserRankMove() throws Exception;

    int updateAllUserRankMoveDef() throws Exception;

    int updateAllUserWeekRankDefAjax() throws Exception;

    int updateAllUserRankDefAjax() throws Exception;

    int updateUserAddTicket(UserDto userDto) throws Exception;

    int updateUserAddTicket2(UserDto userDto) throws Exception;

    int updateUserAddTicket3(UserDto userDto) throws Exception;

    int updateUserAddTicket4(UserDto userDto) throws Exception;

    int updateUserAddTicket5(UserDto userDto) throws Exception;

    int updateUserAddSumRankPoint(UserDto userDto) throws Exception;

    int updateUserMinusSumRank(UserDto userDto) throws Exception;

    int insertMinusTicketAsCnt(UserDto userDto) throws Exception;

    int useTicketAsCnt(UserDto userDto) throws Exception;

    int insertMinusTicketAsCnt2(UserDto userDto) throws Exception;

    int useTicketAsCnt2(UserDto userDto) throws Exception;

    int insertMinusTicketAsCnt3(UserDto userDto) throws Exception;

    int insertMinusTicketAsCnt4(UserDto userDto) throws Exception;

    int insertMinusTicketAsCnt5(UserDto userDto) throws Exception;

    int useTicketAsCnt3(UserDto userDto) throws Exception;

    int useTicketAsCnt4(UserDto userDto) throws Exception;

    int useTicketAsCnt5(UserDto userDto) throws Exception;

    int updateTicket1ResetAjax(UserDto userDto) throws Exception;

    int insertCouponHistory(UserDto userDto) throws Exception;

    int updateAllUserWeekTicketDefAjax() throws Exception;

    int updateAllUserMonthTicketDefAjax() throws Exception;

    String selectPointSum() throws Exception;

    int selectPrice(TicketBuyDto ticketBuyDto) throws Exception;

    int insertBuyTicketHistory(TicketBuyDto ticketBuyDto) throws Exception;

    int selectTicketHistoryListTotalCount(TicketBuyDto ticketBuyDto) throws Exception;

    List<TicketBuyDto> selectTicketHistoryList(TicketBuyDto ticketBuyDto) throws Exception;

}
