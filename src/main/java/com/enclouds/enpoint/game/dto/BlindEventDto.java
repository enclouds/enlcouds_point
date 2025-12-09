package com.enclouds.enpoint.game.dto;

import lombok.Data;

@Data
public class BlindEventDto {

    private String roomId;
    private String action;
    private int level;
    private int smallBlind;
    private int bigBlind;
    private int antes;
    private int timer;

}
