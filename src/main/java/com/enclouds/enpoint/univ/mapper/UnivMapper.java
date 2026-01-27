package com.enclouds.enpoint.univ.mapper;

import com.enclouds.enpoint.univ.dto.UnivDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UnivMapper {

    int selectUnivListTotalCount(UnivDto univDto) throws Exception;

    List<UnivDto> selectUnivList(UnivDto univDto) throws Exception;

    List<UnivDto> selectUnivTotalList() throws Exception;

    int insertUniv(UnivDto univDto) throws Exception;

    int updateUniv(UnivDto univDto) throws Exception;

    int deleteUniv(UnivDto univDto) throws Exception;

}
