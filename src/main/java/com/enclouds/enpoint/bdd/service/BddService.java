package com.enclouds.enpoint.bdd.service;

import com.enclouds.enpoint.bdd.dto.BddDto;

import java.util.List;

public interface BddService {

    List<BddDto> selectBddList(BddDto bddDto) throws Exception;

    int insertBdd(BddDto bddDto) throws Exception;

    int updateBdd(BddDto bddDto) throws Exception;

    int deleteBdd(BddDto bddDto) throws Exception;

    BddDto selectBddDetail(BddDto bddDto) throws Exception;

}
