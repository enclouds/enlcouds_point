package com.enclouds.enpoint.tournament.dto;

import com.enclouds.enpoint.cmm.dto.CommonDto;
import lombok.Data;

@Data
public class TournamentDto extends CommonDto {

    private long seq;
    private String rnum;
    private String title;
    private String tournamentName;
    private String tournamentSeq;
    private String phoneNum;
    private String regPhoneNum;
    private String nickName;
    private String regId;
    private String regUserId;
    private String regDate;
    private String startDate;
    private String ticket5;
    private String ticket4;
    private String ticket2;
    private int agentCode;
    private String infoDesc;
    private int entryCount;
    private int totalEntryCount;
    private String onlineChecked;
    private int klptCnt;
    private int inviCnt;
    private int freeCnt;
    private int ticketCnt;

    private int totalKlptCnt;
    private int totalInviCnt;
    private int totalFreeCnt;

    private String regName;
    private String agentName;
    private String bankNum;
    private int prize;
    private int addPrize;
    private String prizeStr;
    private String addPrizeStr;
    private int rankNum;
    private String addPrizeYn;

    private String memo;

}