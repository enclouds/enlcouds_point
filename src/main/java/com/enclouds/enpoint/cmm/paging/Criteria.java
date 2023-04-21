package com.enclouds.enpoint.cmm.paging;

import lombok.Data;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class Criteria {
	
	/** 현재 페이지 번호 */
	private int currentPageNo;

	/** 페이지당 출력할 데이터 개수 */
	private int recordsPerPage;

	/** 화면 하단에 출력할 페이지 사이즈 */
	private int pageSize;

	/** 검색 키워드 */
	private String searchKeyword;

	/** 검색 유형 */
	private String searchType;

	private String schCond1;
	private String schCond2;
	private String schCond3;
	private String schCond4;
	private String schCond5;
	private String schCond6;
	private String schCond7;
	private String schStartDte;
	private String schEndDte;
	private String schText;
	private String schText2;
	private String schYear;
	private String schDate;
	private String schChkRemain;
	private String schChkExpired;
	private String schChkRecvAmt;
	private String schChkDiscount;
	private String schChkLatDate;
	private String schChkKm;
	private Integer schAgentCode;

	private boolean excelFlag;

	public Criteria() {
		this.currentPageNo = 1;
		this.recordsPerPage = 10;
		this.pageSize = 10;
	}
	
	public String makeQueryString(int pageNo) {

		UriComponents uriComponents = UriComponentsBuilder.newInstance()
				.queryParam("currentPageNo", pageNo)
				.queryParam("recordsPerPage", recordsPerPage)
				.queryParam("pageSize", pageSize)
				.queryParam("schCond1", schCond1)
				.queryParam("schCond2", schCond2)
				.queryParam("schCond3", schCond3)
				.queryParam("schCond4", schCond4)
				.queryParam("schCond5", schCond5)
				.queryParam("schCond6", schCond6)
				.queryParam("schCond7", schCond7)
				.queryParam("schStartDte", schStartDte)
				.queryParam("schEndDte", schEndDte)
				.queryParam("schDate", schDate)
				.queryParam("schText", schText)
				.queryParam("schText2", schText2)
				.queryParam("schYear", schYear)
				.queryParam("schChkRemain", schChkRemain)
				.queryParam("schChkExpired", schChkExpired)
				.queryParam("schChkRecvAmt", schChkRecvAmt)
				.queryParam("schChkDiscount", schChkDiscount)
				.queryParam("schChkLatDate", schChkLatDate)
				.queryParam("schChkKm", schChkKm)
				.build()
				.encode();

		return uriComponents.toUriString();
	}
	
}
