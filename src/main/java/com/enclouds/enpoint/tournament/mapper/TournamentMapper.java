package com.enclouds.enpoint.tournament.mapper;

import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.tournament.dto.TournamentDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TournamentMapper {

    int selectTournamentListTotalCount(TournamentDto tournamentDto) throws Exception;

    List<TournamentDto> selectTournamentList(TournamentDto tournamentDto) throws Exception;

    List<TournamentDto> selectTournamentRegList(TournamentDto tournamentDto) throws Exception;

    public int insertTournament(TournamentDto tournamentDto) throws Exception;

    public int updateTournament(TournamentDto tournamentDto) throws Exception;

    public int deleteTournamentRegInfo(TournamentDto tournamentDto) throws Exception;

    public int deleteTournament(TournamentDto tournamentDto) throws Exception;

    public int registration(TournamentDto tournamentDto) throws Exception;

    public int registrationCount(TournamentDto tournamentDto) throws Exception;

    public TournamentDto selectPrintInfo(TournamentDto tournamentDto) throws Exception;

    public long selectLastInsertId() throws Exception;

}
