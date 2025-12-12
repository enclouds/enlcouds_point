package com.enclouds.enpoint.tournament.service;

import com.enclouds.enpoint.tournament.dto.TournamentDto;

import java.util.HashMap;
import java.util.List;

public interface TournamentService {

    List<TournamentDto> selectTournamentList(TournamentDto tournamentDto) throws Exception;

    List<TournamentDto> selectTournamentRegList(TournamentDto tournamentDto) throws Exception;

    List<TournamentDto> selectTournamentPrizeList(TournamentDto tournamentDto) throws Exception;

    TournamentDto selectTournamentRegTotalCnt(TournamentDto tournamentDto) throws Exception;

    public int insertTournament(TournamentDto tournamentDto) throws Exception;

    public int updateTournament(TournamentDto tournamentDto) throws Exception;

    public int updateMemo(TournamentDto tournamentDto) throws Exception;

    public int deleteTournament(TournamentDto tournamentDto) throws Exception;

    public int deletePrize(TournamentDto tournamentDto) throws Exception;

    public HashMap<String, Object> registration(TournamentDto tournamentDto) throws Exception;

    public int prizeInsert(TournamentDto tournamentDto) throws Exception;

    public int updatePrize(TournamentDto tournamentDto) throws Exception;

    public TournamentDto getPrintInfo(TournamentDto tournamentDto) throws Exception;

    public int updateAddPrize(TournamentDto tournamentDto) throws Exception;

    public TournamentDto selectPrintInfo(TournamentDto tournamentDto) throws Exception;

}
