package com.enclouds.enpoint.game.controller;

import com.enclouds.enpoint.game.dto.BlindEventDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class BlindSyncController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    // 클라이언트 → 서버 → 모든 지점 broadcast
    @MessageMapping("/event")
    public void broadcastBlindEvent(BlindEventDto event) {
        messagingTemplate.convertAndSend("/topic/blind", event);
    }

}
