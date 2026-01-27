package com.enclouds.enpoint.klpi.mapper;

import com.enclouds.enpoint.klpi.dto.KlpiDto;
import com.enclouds.enpoint.klpi.dto.KlpiRankDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface KlpiMapper {

    public int deleteKlpi(KlpiDto klpiDto) throws Exception;

    public int insertKlpi(KlpiDto klpiDto) throws Exception;

    public List<KlpiRankDto> selectKlpiRankList(String gbn) throws Exception;

    public List<KlpiRankDto> selectKlpiRankListUniv(String gbn) throws Exception;

}
