package com.enclouds.enpoint.klpi.service;

import com.enclouds.enpoint.klpi.dto.KlpiDto;
import com.enclouds.enpoint.klpi.dto.KlpiRankDto;

import java.util.List;

public interface KlpiService {

    public int deleteKlpi(KlpiDto klpiDto) throws Exception;

    public int insertKlpi(KlpiDto klpiDto) throws Exception;

    public List<KlpiRankDto> selectKlpiRankList(String gbn) throws Exception;

}
