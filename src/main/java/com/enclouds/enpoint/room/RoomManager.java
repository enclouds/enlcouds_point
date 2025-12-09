package com.enclouds.enpoint.room;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RoomManager {

    // 현재 존재하는 ROOM 목록
    private final Map<Long, Set<String>> roomMap = new ConcurrentHashMap<>();

    public void addRoom(Long seq, String roomId) {
        roomMap.computeIfAbsent(seq, k -> ConcurrentHashMap.newKeySet())
                .add(roomId);
    }

    public Set<String> getRoomsByGameSeq(Long seq) {
        return roomMap.getOrDefault(seq, Collections.emptySet());
    }

}
