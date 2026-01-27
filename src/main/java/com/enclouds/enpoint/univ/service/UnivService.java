package com.enclouds.enpoint.univ.service;

import com.enclouds.enpoint.game.dto.GameDto;
import com.enclouds.enpoint.univ.dto.UnivDto;

import java.util.List;

public interface UnivService {

    List<UnivDto> selectUnivList(UnivDto univDto) throws Exception;

    List<UnivDto> selectUnivTotalList() throws Exception;

    int insertUniv(UnivDto univDto) throws Exception;

    int updateUniv(UnivDto univDto) throws Exception;

    int deleteUniv(UnivDto univDto) throws Exception;

}
