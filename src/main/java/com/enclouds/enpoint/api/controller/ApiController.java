package com.enclouds.enpoint.api.controller;

import com.enclouds.enpoint.api.dto.*;
import com.enclouds.enpoint.api.service.ApiService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.CustomUserService;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ApiController {

    @Autowired
    private ApiService apiService;

    @RequestMapping(method = RequestMethod.POST, path = "/getUserInfo")
    @ResponseBody
    public ApiUserRtnDto getUserInfo(@RequestBody ApiDto apiDto) throws Exception{

        ApiUserRtnDto apiUserRtnDto = apiService.selectUserInfo(apiDto);

        if(apiUserRtnDto != null){
            apiUserRtnDto.setResultCode("0000");
            apiUserRtnDto.setResultMsg("정상");
        }else {
            ApiUserRtnDto apiUserRtnDto2 = new ApiUserRtnDto();
            apiUserRtnDto2.setResultCode("E001");
            apiUserRtnDto2.setResultMsg("조회된 사용자 정보가 없습니다.");

            return apiUserRtnDto2;
        }

        return apiUserRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getPointHistory")
    @ResponseBody
    public ApiPointHistoryListDto getPointHistory(@RequestBody ApiDto apiDto) throws Exception{

        List<ApiPointHistoryDto> pointHistoryDtoList = apiService.getPointHistory(apiDto);

        ApiPointHistoryListDto apiPointHistoryListDto = new ApiPointHistoryListDto();
        if(pointHistoryDtoList != null){
            apiPointHistoryListDto.setPointHistoryList(pointHistoryDtoList);
            apiPointHistoryListDto.setResultCode("0000");
            apiPointHistoryListDto.setResultMsg("정상");
        }else {
            apiPointHistoryListDto.setResultCode("E001");
            apiPointHistoryListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiPointHistoryListDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addPoint")
    @ResponseBody
    public ApiRtnDto addPoint(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.addPoint(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else {
            apiRtnDto.setResultCode("E002");
            apiRtnDto.setResultMsg("적립에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/minusPoint")
    @ResponseBody
    public ApiRtnDto minusPoint(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.minusPoint(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else if(result == -1){
            apiRtnDto.setResultCode("E099");
            apiRtnDto.setResultMsg("보유 포인트가 차감될 포인트보다 작습니다.");
        }else {
            apiRtnDto.setResultCode("E003");
            apiRtnDto.setResultMsg("차감에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getTicketHistory")
    @ResponseBody
    public ApiTicketHistoryListDto getTicketHistory(@RequestBody ApiDto apiDto) throws Exception{

        List<ApiTicketHistoryDto> ticketHistoryDtoList = apiService.getTicketHistory(apiDto);

        ApiTicketHistoryListDto apiTicketHistoryListDto = new ApiTicketHistoryListDto();
        if(ticketHistoryDtoList != null){
            apiTicketHistoryListDto.setTicketHistoryList(ticketHistoryDtoList);
            apiTicketHistoryListDto.setResultCode("0000");
            apiTicketHistoryListDto.setResultMsg("정상");
        }else {
            apiTicketHistoryListDto.setResultCode("E001");
            apiTicketHistoryListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiTicketHistoryListDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addTicket")
    @ResponseBody
    public ApiRtnDto addTicket(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.addTicket(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else {
            apiRtnDto.setResultCode("E004");
            apiRtnDto.setResultMsg("티켓 적립에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/minusTicket")
    @ResponseBody
    public ApiRtnDto minusTicket(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.minusTicket(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else if(result == -1){
            apiRtnDto.setResultCode("E099");
            apiRtnDto.setResultMsg("보유 티켓수량이 차감될 티켓 수량 보다 작습니다.");
        }else {
            apiRtnDto.setResultCode("E005");
            apiRtnDto.setResultMsg("티켓 사용에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addCoupon")
    @ResponseBody
    public ApiRtnDto addCoupon(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.addCoupon(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else {
            apiRtnDto.setResultCode("E004");
            apiRtnDto.setResultMsg("쿠폰 적립에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addWinPoint")
    @ResponseBody
    public ApiRtnDto addWinPoint(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.addWinPoint(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else {
            apiRtnDto.setResultCode("E004");
            apiRtnDto.setResultMsg("승점 적립에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getRankList")
    @ResponseBody
    public ApiRankListDto getRankList(@RequestBody ApiDto apiDto) throws Exception{

        List<ApiRankDto> rankDtoList = apiService.getRankList(apiDto);

        ApiRankListDto apiRankListDto = new ApiRankListDto();
        if(rankDtoList != null){
            apiRankListDto.setRankList(rankDtoList);
            apiRankListDto.setResultCode("0000");
            apiRankListDto.setResultMsg("정상");
        }else {
            apiRankListDto.setResultCode("E001");
            apiRankListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiRankListDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getTicketRankList")
    @ResponseBody
    public ApiTicketRankListDto getTicketRankList(@RequestBody ApiDto apiDto) throws Exception{

        List<ApiTicketRankDto> ticketRankDtoList = apiService.getTicketRankList(apiDto);

        ApiTicketRankListDto apiTicketRankListDto = new ApiTicketRankListDto();
        if(ticketRankDtoList != null){
            apiTicketRankListDto.setTicketRankList(ticketRankDtoList);
            apiTicketRankListDto.setResultCode("0000");
            apiTicketRankListDto.setResultMsg("정상");
        }else {
            apiTicketRankListDto.setResultCode("E001");
            apiTicketRankListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiTicketRankListDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addJackpot")
    @ResponseBody
    public ApiRtnDto addJackpot(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.addJackpot(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else {
            apiRtnDto.setResultCode("E005");
            apiRtnDto.setResultMsg("잭팟 적립에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

}
