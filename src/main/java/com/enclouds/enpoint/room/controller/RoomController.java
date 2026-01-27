package com.enclouds.enpoint.room.controller;

import com.enclouds.enpoint.room.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    // 룸 목록 (네 list.html에서 이미 호출 중)
    @GetMapping
    public List<String> getRooms(@RequestParam("seq") Long gameSeq) {
        return roomService.getRoomCodes(gameSeq);
    }

    // 시작 시 룸 생성/재사용
    @PostMapping("/create")
    public String createRoom(@RequestParam("seq") Long gameSeq) {
        return roomService.createOrGetRoom(gameSeq);
    }
}
