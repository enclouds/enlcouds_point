package com.enclouds.enpoint.room.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RoomStatePayloadDto {
    private String room;

    // 최소 동기화 필드들
    private int blindLvl;         // 0-based index
    private int remainingSec;     // 남은 초
    private int player;
    private int totalPlayer;
    private String status;        // RUNNING / PAUSED

    private int nextBreakSec;
    private int avgStack;
    private int totalRunningSec;
}
