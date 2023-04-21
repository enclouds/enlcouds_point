package com.enclouds.enpoint.jackpot.service;

import com.enclouds.enpoint.jackpot.dto.JackpotDto;

import java.util.List;

public interface JackpotService {

    List<JackpotDto> selectJackpotList(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotListTotal(JackpotDto jackpotDto) throws Exception;

    int insertJackpot(JackpotDto jackpotDto) throws Exception;

    int updateJackpot(JackpotDto jackpotDto) throws Exception;

    int deleteJackpot(JackpotDto jackpotDto) throws Exception;

    JackpotDto selectJackPotInfo(JackpotDto jackpotDto) throws Exception;

    int jackpotAddPointAjax(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotNotiList(JackpotDto jackpotDto) throws Exception;

    int insertNotiAjax(JackpotDto jackpotDto) throws Exception;

    int updateNotiAjax(JackpotDto jackpotDto) throws Exception;

    int deleteNotiAjax(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotNotiListTotal(JackpotDto jackpotDto) throws Exception;

    JackpotDto selectJackPotInfoTotal(JackpotDto jackpotDto) throws Exception;

    int addJackpotAjax(JackpotDto jackpotDto) throws Exception;

    int minusJackpotAjax(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotHistoryList(JackpotDto jackpotDto) throws Exception;

}
