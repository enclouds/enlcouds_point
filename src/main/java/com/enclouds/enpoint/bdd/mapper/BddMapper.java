package com.enclouds.enpoint.bdd.mapper;

import com.enclouds.enpoint.bdd.dto.BddDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BddMapper {

    int selectBddListTotalCount(BddDto bddDto) throws Exception;

    List<BddDto> selectBddList(BddDto bddDto) throws Exception;

    int insertBdd(BddDto bddDto) throws Exception;

    int updateBdd(BddDto bddDto) throws Exception;

    int deleteBdd(BddDto bddDto) throws Exception;

    BddDto selectBddDetail(BddDto bddDto) throws Exception;

    void updateBddDetailCnt(BddDto bddDto) throws Exception;

}
