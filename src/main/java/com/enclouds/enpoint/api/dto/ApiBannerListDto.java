package com.enclouds.enpoint.api.dto;

import lombok.Data;

import java.util.List;

@Data
public class ApiBannerListDto extends ApiRtnDto{

    private List<ApiBannerDto> bannerList;

}
