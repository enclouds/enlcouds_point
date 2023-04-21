package com.enclouds.enpoint.jackpot.mapper;

import com.enclouds.enpoint.jackpot.dto.JackpotDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface JackpotMapper {

    int selectJackpotListTotalCount(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotList(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotListTotal(JackpotDto jackpotDto) throws Exception;

    int selectJackpotNotiListTotalCount(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotNotiList(JackpotDto jackpotDto) throws Exception;

    int insertJackpot(JackpotDto jackpotDto) throws Exception;

    int updateJackpot(JackpotDto jackpotDto) throws Exception;

    int deleteJackpot(JackpotDto jackpotDto) throws Exception;

    JackpotDto selectJackPotInfo(JackpotDto jackpotDto) throws Exception;

    int updateJackpotPoint(JackpotDto jackpotDto) throws Exception;

    int insertAddJackpot(JackpotDto jackpotDto) throws Exception;

    int insertNotiAjax(JackpotDto jackpotDto) throws Exception;

    int updateNotiAjax(JackpotDto jackpotDto) throws Exception;

    int deleteNotiAjax(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotNotiListTotal(JackpotDto jackpotDto) throws Exception;

    JackpotDto selectJackPotInfoTotal(JackpotDto jackpotDto) throws Exception;

    int addJackpotAjax(JackpotDto jackpotDto) throws Exception;

    int minusJackpotAjax(JackpotDto jackpotDto) throws Exception;

    List<JackpotDto> selectJackpotHistoryList(JackpotDto jackpotDto) throws Exception;

    int selectJackpotHistoryListTotalCount(JackpotDto jackpotDto) throws Exception;

}
