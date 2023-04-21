package com.enclouds.enpoint.cmm.dto;

import com.enclouds.enpoint.cmm.paging.Criteria;
import com.enclouds.enpoint.cmm.paging.PaginationInfo;
import lombok.Data;

@Data
public class CommonDto extends Criteria {
	
	private PaginationInfo paginationInfo;

	private boolean excelFlag = false;
}
