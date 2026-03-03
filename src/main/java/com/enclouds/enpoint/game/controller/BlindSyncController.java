package com.enclouds.enpoint.game.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BlindSyncController {

    /*@Autowired
    private SimpMessagingTemplate messagingTemplate;*/

    // 클라이언트 → 서버 → 모든 지점 broadcast
   /* @MessageMapping("/event")
    public void broadcastBlindEvent(BlindEventDto event) {
        messagingTemplate.convertAndSend("/topic/blind", event);
    }*/

}
