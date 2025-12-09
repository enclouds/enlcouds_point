package com.enclouds.enpoint.tournament.service;

import com.enclouds.enpoint.tournament.dto.TournamentDto;

import java.util.List;

public interface TournamentService {

    List<TournamentDto> selectTournamentList(TournamentDto tournamentDto) throws Exception;

    List<TournamentDto> selectTournamentRegList(TournamentDto tournamentDto) throws Exception;

    public int insertTournament(TournamentDto tournamentDto) throws Exception;

    public int updateTournament(TournamentDto tournamentDto) throws Exception;

    public int deleteTournament(TournamentDto tournamentDto) throws Exception;

    public int registration(TournamentDto tournamentDto) throws Exception;

    public TournamentDto getPrintInfo(TournamentDto tournamentDto) throws Exception;

}
