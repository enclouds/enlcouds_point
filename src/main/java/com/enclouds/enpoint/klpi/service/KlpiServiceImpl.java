package com.enclouds.enpoint.klpi.service;

import com.enclouds.enpoint.klpi.dto.KlpiDto;
import com.enclouds.enpoint.klpi.dto.KlpiRankDto;
import com.enclouds.enpoint.klpi.mapper.KlpiMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlpiServiceImpl implements KlpiService{

    @Autowired
    private KlpiMapper klpiMapper;

    @Override
    public int deleteKlpi(KlpiDto klpiDto) throws Exception{
        return klpiMapper.deleteKlpi(klpiDto);
    }

    @Override
    public int insertKlpi(KlpiDto klpiDto) throws Exception{
        return klpiMapper.insertKlpi(klpiDto);
    }

    @Override
    public List<KlpiRankDto> selectKlpiRankList(String gbn) throws Exception{
        List<KlpiRankDto> klpiRankDtoList = new ArrayList<>();

        if(gbn.equals("N")){
            klpiRankDtoList = klpiMapper.selectKlpiRankList(gbn);
        }else {
            klpiRankDtoList = klpiMapper.selectKlpiRankListUniv(gbn);
        }
        return klpiRankDtoList;
    }

}
