package com.enclouds.enpoint.api.controller;

import com.enclouds.enpoint.agent.service.AgentService;
import com.enclouds.enpoint.api.dto.*;
import com.enclouds.enpoint.api.service.ApiService;
import com.enclouds.enpoint.user.dto.UserDto;
import com.enclouds.enpoint.user.service.CustomUserService;
import com.enclouds.enpoint.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
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

    //선결제 지점 API
    @RequestMapping(method = RequestMethod.POST, path = "/pre/addPoint")
    @ResponseBody
    public ApiRtnDto preAddPoint(@RequestBody ApiPreDto apiPreDto) throws Exception{
        ApiRtnDto apiRtnDto = new ApiRtnDto();

        if(apiPreDto.getApiKey().equals("1fodTP6QWCAGUp9")){
            int result = apiService.preAddPoint(apiPreDto);

            if(result > 0){
                if(result == 999){
                    apiRtnDto.setResultCode("E001");
                    apiRtnDto.setResultMsg("회원으로 등록되어 있지 않은 핸드폰 번호 입니다.");
                }else {
                    apiRtnDto.setResultCode("0000");
                    apiRtnDto.setResultMsg("정상처리");
                }
            }else {
                apiRtnDto.setResultCode("E002");
                apiRtnDto.setResultMsg("적립에 실패 하였습니다.");
            }
        }else {
            apiRtnDto.setResultCode("E999");
            apiRtnDto.setResultMsg("API Key Not Match.");
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

    @RequestMapping(method = RequestMethod.POST, path = "/getBannerList")
    @ResponseBody
    public ApiBannerListDto getBannerList() throws Exception{

        List<ApiBannerDto> bannerDtoList = apiService.getBannerList();

        ApiBannerListDto apiBannerListDto = new ApiBannerListDto();
        if(bannerDtoList != null){
            if(bannerDtoList.isEmpty()) {
                apiBannerListDto.setResultCode("E001");
                apiBannerListDto.setResultMsg("조회된 정보가 없습니다.");
            }else {
                apiBannerListDto.setBannerList(bannerDtoList);
                apiBannerListDto.setResultCode("0000");
                apiBannerListDto.setResultMsg("정상");
            }
        }else {
            apiBannerListDto.setResultCode("E001");
            apiBannerListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiBannerListDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/getAgentList")
    @ResponseBody
    public ApiAgentListDto getAgentList() throws Exception{

        List<ApiAgentDto> agentDtoList = apiService.getAgentList();
        List<ApiTicketDto> ticketDtoList = apiService.getTicketList();

        ApiAgentListDto apiAgentListDto = new ApiAgentListDto();
        if(agentDtoList != null){
            if(agentDtoList.isEmpty()) {
                apiAgentListDto.setResultCode("E001");
                apiAgentListDto.setResultMsg("조회된 정보가 없습니다.");
            }else {
                apiAgentListDto.setAgentList(agentDtoList);
                apiAgentListDto.setTicketList(ticketDtoList);
                apiAgentListDto.setResultCode("0000");
                apiAgentListDto.setResultMsg("정상");
            }
        }else {
            apiAgentListDto.setResultCode("E001");
            apiAgentListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiAgentListDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/addKpChip")
    @ResponseBody
    public ApiRtnDto addKpChip(@RequestBody ApiDto apiDto) throws Exception{

        int result = apiService.addKpChip(apiDto);

        ApiRtnDto apiRtnDto = new ApiRtnDto();
        if(result > 0){
            apiRtnDto.setResultCode("0000");
            apiRtnDto.setResultMsg("정상");
        }else {
            apiRtnDto.setResultCode("E004");
            apiRtnDto.setResultMsg("킹스포커 포인트 갱신에 실패 하였습니다.");
        }

        return apiRtnDto;
    }

    @RequestMapping(method = RequestMethod.POST, path = "/ticketChangePrice")
    @ResponseBody
    public ApiTicketChangePriceListDto ticketChangePrice() throws Exception{

        List<ApiTicketAsPriceDto> ApiTicketAsPriceList = apiService.ticketChangePrice();

        ApiTicketChangePriceListDto apiTicketChangePriceListDto = new ApiTicketChangePriceListDto();
        if(ApiTicketAsPriceList != null){
            if(ApiTicketAsPriceList.isEmpty()){
                apiTicketChangePriceListDto.setResultCode("E001");
                apiTicketChangePriceListDto.setResultMsg("조회된 정보가 없습니다.");
            }else {
                apiTicketChangePriceListDto.setTicketList(ApiTicketAsPriceList);
                apiTicketChangePriceListDto.setResultCode("0000");
                apiTicketChangePriceListDto.setResultMsg("정상");
            }
        }else {
            apiTicketChangePriceListDto.setResultCode("E001");
            apiTicketChangePriceListDto.setResultMsg("조회된 정보가 없습니다.");
        }

        return apiTicketChangePriceListDto;
    }

}
