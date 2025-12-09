package com.enclouds.enpoint.room.controller;

import com.enclouds.enpoint.room.RoomManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomManager roomManager;

    @GetMapping
    public Set<String> getRooms(@RequestParam Long seq) {
        return roomManager.getRoomsByGameSeq(seq);
    }
}
