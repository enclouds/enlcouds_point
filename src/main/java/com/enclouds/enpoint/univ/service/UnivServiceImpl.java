package com.enclouds.enpoint.univ.service;

import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import com.enclouds.enpoint.univ.dto.UnivDto;
import com.enclouds.enpoint.univ.mapper.UnivMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnivServiceImpl implements UnivService{

    @Autowired
    private UnivMapper univMapper;

    @Override
    public List<UnivDto> selectUnivList(UnivDto univDto) throws Exception {
        int univTotalCount = univMapper.selectUnivListTotalCount(univDto);

        PaginationInfo paginationInfo = new PaginationInfo(univDto);
        paginationInfo.setTotalRecordCount(univTotalCount);
        univDto.setPaginationInfo(paginationInfo);

        return univMapper.selectUnivList(univDto);
    }

    @Override
    public List<UnivDto> selectUnivTotalList() throws Exception {
        return univMapper.selectUnivTotalList();
    }

    @Override
    public int insertUniv(UnivDto univDto) throws Exception {
        return univMapper.insertUniv(univDto);
    }

    @Override
    public int updateUniv(UnivDto univDto) throws Exception {
        return univMapper.updateUniv(univDto);
    }

    @Override
    public int deleteUniv(UnivDto univDto) throws Exception {
        return univMapper.deleteUniv(univDto);
    }

}
