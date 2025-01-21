package com.enclouds.enpoint.game.mapper;

import com.enclouds.enpoint.agent.dto.AgentDto;
import com.enclouds.enpoint.game.dto.BlindDto;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.game.dto.PrizeDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GameMapper {

    int selectGameListTotalCount(GameDto gameDto) throws Exception;

    List<GameDto> selectGameList(GameDto gameDto) throws Exception;

    List<GameDto> selectGameListByBlind(GameDto gameDto) throws Exception;

    List<GameDto> selectGameListTotal(GameDto gameDto) throws Exception;

    List<BlindDto> selectBlindList(BlindDto blindDto) throws Exception;

    List<PrizeDto> selectPrizeList(PrizeDto prizeDto) throws Exception;
    List<PrizeDto> selectPrizeList1(PrizeDto prizeDto) throws Exception;
    List<PrizeDto> selectPrizeList2(PrizeDto prizeDto) throws Exception;

    int insertGame(GameDto gameDto) throws Exception;

    int insertBlind(BlindDto blindDto) throws Exception;

    int insertPrize(PrizeDto prizeDto) throws Exception;

    int updateGame(GameDto gameDto) throws Exception;

    int updateBlind(BlindDto blindDto) throws Exception;

    int updatePrize(PrizeDto prizeDto) throws Exception;

    int deleteGame(GameDto gameDto) throws Exception;

    int deleteBlind(BlindDto blindDto) throws Exception;

    int deletePrize(PrizeDto prizeDto) throws Exception;

    GameDto selectGameInfo(GameDto gameDto) throws Exception;

}
