package com.enclouds.enpoint.bdd.service;

import com.enclouds.enpoint.bdd.dto.BddDto;
import com.enclouds.enpoint.bdd.mapper.BddMapper;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BddServiceImpl implements BddService{

    @Autowired
    private BddMapper bddMapper;

    @Override
    public List<BddDto> selectBddList(BddDto bddDto) throws Exception {
        int bddTotalCount = bddMapper.selectBddListTotalCount(bddDto);

        PaginationInfo paginationInfo = new PaginationInfo(bddDto);
        paginationInfo.setTotalRecordCount(bddTotalCount);
        bddDto.setPaginationInfo(paginationInfo);

        return bddMapper.selectBddList(bddDto);
    }

    @Override
    public int insertBdd(BddDto bddDto) throws Exception {
        return bddMapper.insertBdd(bddDto);
    }

    @Override
    public int updateBdd(BddDto bddDto) throws Exception {
        return bddMapper.updateBdd(bddDto);
    }

    @Override
    public int deleteBdd(BddDto bddDto) throws Exception {
        return bddMapper.deleteBdd(bddDto);
    }

    @Override
    public BddDto selectBddDetail(BddDto bddDto) throws Exception {
        bddMapper.updateBddDetailCnt(bddDto);

        return bddMapper.selectBddDetail(bddDto);
    }
}
