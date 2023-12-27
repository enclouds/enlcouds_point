package com.enclouds.enpoint.user.service;

import com.enclouds.enpoint.user.dto.CouponDto;
import com.enclouds.enpoint.user.dto.PointDto;
import com.enclouds.enpoint.user.dto.UserDto;
import org.springframework.security.core.userdetails.User;

import java.util.List;

public interface CustomUserService {

    List<UserDto> selectCustomUserList(UserDto userDto) throws Exception;

    List<UserDto> selectCustomUserListByVisit(UserDto userDto) throws Exception;

    UserDto selectCustomUserInfo(UserDto userDto) throws Exception;

    List<PointDto> selectUserPointList(PointDto pointDto) throws Exception;

    PointDto selectUserPointTotal(PointDto pointDto) throws Exception;

    List<CouponDto> selectUserCouponList(CouponDto couponDto) throws Exception;

    CouponDto selectUserCouponTotal(CouponDto couponDto) throws Exception;

    int selectDuplUser(UserDto userDto) throws Exception;

    int insertUser(UserDto userDto) throws Exception;

    int updateUser(UserDto userDto) throws Exception;

    int deleteUser(UserDto userDto) throws Exception;

    int updateUserAddPoint(UserDto userDto) throws Exception;

    int updateUserAddTicket(UserDto userDto) throws Exception;

    int updateUserAddTicket2(UserDto userDto) throws Exception;

    int updateUserAddTicket3(UserDto userDto) throws Exception;

    int useTicket(UserDto userDto) throws Exception;

    int useTicket2(UserDto userDto) throws Exception;

    int useTicket3(UserDto userDto) throws Exception;

    int updateUserAddRankPoint(UserDto userDto) throws Exception;

    int updateUserMinusRankPoint(UserDto userDto) throws Exception;

    int updateUserMinusPoint(UserDto userDto) throws Exception;

    int updateUserMinusTicket(UserDto userDto) throws Exception;

    int updateUserMinusTicket2(UserDto userDto) throws Exception;

    int updateUserMinusTicket3(UserDto userDto) throws Exception;

    int updateUserHumanAjax(UserDto userDto) throws Exception;

    int updateUserRankDefAjax(UserDto userDto) throws Exception;

    List<PointDto> selectPointHistory(UserDto userDto) throws Exception;

    List<UserDto> selectUserRankList(UserDto userDto) throws Exception;

    List<UserDto> selectUserTicketRankList(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory2(UserDto userDto) throws Exception;

    List<PointDto> selectTicketHistory3(UserDto userDto) throws Exception;

    List<CouponDto> selectCouponHistory(UserDto userDto) throws Exception;

    int updateUserAddCouponPoint(UserDto userDto) throws Exception;

    int updateUserMinusCouponPoint(UserDto userDto) throws Exception;

    void updateWeekRankPoint() throws Exception;

    int updateAllUserRankMoveAjax() throws Exception;

    int updateAllUserWeekRankDefAjax() throws Exception;

    int updateAllUserRankDefAjax() throws Exception;

    int updateUserAddSumRankPoint(UserDto userDto) throws Exception;

    int updateUserMinusSumRank(UserDto userDto) throws Exception;

}
