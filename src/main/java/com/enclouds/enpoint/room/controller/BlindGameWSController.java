package com.enclouds.enpoint.room.controller;

import com.enclouds.enpoint.game.dto.BlindEventDto;
import com.enclouds.enpoint.room.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BlindGameWSController {

    @Autowired
    private RoomManager roomManager;

    // 클라이언트가 방에 접속하면 실행됨
    @MessageMapping("/join/{seq}/{roomId}")
    public void joinRoom(@DestinationVariable Long seq, @DestinationVariable String roomId) {
        roomManager.addRoom(seq, roomId);
    }

    // 블라인드 이벤트 전달
    @MessageMapping("/game/{roomId}")
    @SendTo("/topic/game/{roomId}")
    public BlindEventDto broadcastEvent(@DestinationVariable String roomId, BlindEventDto event) {
        return event; // 방 전체에 전달
    }

}
