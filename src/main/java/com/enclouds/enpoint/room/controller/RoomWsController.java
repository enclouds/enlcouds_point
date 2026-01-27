package com.enclouds.enpoint.room.controller;

import com.enclouds.enpoint.room.dto.RoomStatePayloadDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.concurrent.ConcurrentHashMap;

@Controller
@RequiredArgsConstructor
public class RoomWsController {

    private final SimpMessagingTemplate messagingTemplate;

    // roomCode -> lastState
    private static final ConcurrentHashMap<String, RoomStatePayloadDto> LAST = new ConcurrentHashMap<>();

    // 컨트롤 화면이 상태를 올리면 저장 + 전광판에 방송
    @MessageMapping("/rooms/{room}/state")
    public void onState(@DestinationVariable String room, RoomStatePayloadDto payload) {
        payload.setRoom(room);
        LAST.put(room, payload);
        messagingTemplate.convertAndSend("/topic/rooms/" + room, payload);
    }

    // 전광판이 접속 직후 "현재 상태 줘" 요청하면 마지막 상태를 1번 쏴줌
    @MessageMapping("/rooms/{room}/sync")
    public void onSync(@DestinationVariable String room) {
        RoomStatePayloadDto last = LAST.get(room);
        if (last != null) {
            messagingTemplate.convertAndSend("/topic/rooms/" + room, last);
        }
    }
}
