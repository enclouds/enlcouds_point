package com.enclouds.enpoint.game.service;

import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.game.dto.BlindDto;
import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.game.dto.PrizeDto;
import com.enclouds.enpoint.game.mapper.GameMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameServiceImpl implements GameService{

    @Autowired
    private GameMapper gameMapper;

    @Override
    public List<GameDto> selectGameList(GameDto gameDto) throws Exception {
        int gameTotalCount = gameMapper.selectGameListTotalCount(gameDto);

        PaginationInfo paginationInfo = new PaginationInfo(gameDto);
        paginationInfo.setTotalRecordCount(gameTotalCount);
        gameDto.setPaginationInfo(paginationInfo);

        return gameMapper.selectGameList(gameDto);
    }

    @Override
    public List<GameDto> selectGameListByBlind(GameDto gameDto) throws Exception {
        return gameMapper.selectGameListByBlind(gameDto);
    }

    @Override
    public List<GameDto> selectGameListTotal(GameDto gameDto) throws Exception {
        return gameMapper.selectGameListTotal(gameDto);
    }

    @Override
    public List<BlindDto> selectBlindList(BlindDto blindDto) throws Exception {
        return gameMapper.selectBlindList(blindDto);
    }

    @Override
    public List<PrizeDto> selectPrizeList(PrizeDto prizeDto) throws Exception {
        return gameMapper.selectPrizeList(prizeDto);
    }

    @Override
    public List<PrizeDto> selectPrizeList1(PrizeDto prizeDto) throws Exception {
        return gameMapper.selectPrizeList1(prizeDto);
    }

    @Override
    public List<PrizeDto> selectPrizeList2(PrizeDto prizeDto) throws Exception {
        return gameMapper.selectPrizeList2(prizeDto);
    }

    @Override
    public int insertGame(GameDto gameDto) throws Exception {
        return gameMapper.insertGame(gameDto);
    }

    @Override
    public int insertBlind(BlindDto blindDto) throws Exception {
        return gameMapper.insertBlind(blindDto);
    }

    @Override
    public int insertPrize(PrizeDto prizeDto) throws Exception {
        return gameMapper.insertPrize(prizeDto);
    }

    @Override
    public int updateGame(GameDto gameDto) throws Exception {
        return gameMapper.updateGame(gameDto);
    }

    @Override
    public int updateBlind(BlindDto blindDto) throws Exception {
        return gameMapper.updateBlind(blindDto);
    }

    @Override
    public int updatePrize(PrizeDto prizeDto) throws Exception {
        return gameMapper.updatePrize(prizeDto);
    }

    @Override
    public int deleteGame(GameDto gameDto) throws Exception {
        return gameMapper.deleteGame(gameDto);
    }

    @Override
    public int deleteBlind(BlindDto blindDto) throws Exception {
        return gameMapper.deleteBlind(blindDto);
    }

    @Override
    public int deletePrize(PrizeDto prizeDto) throws Exception {
        return gameMapper.deletePrize(prizeDto);
    }

    @Override
    public GameDto selectGameInfo(GameDto gameDto) throws Exception {
        return gameMapper.selectGameInfo(gameDto);
    }

}
