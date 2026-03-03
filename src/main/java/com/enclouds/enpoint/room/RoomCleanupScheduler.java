package com.enclouds.enpoint.room;

import com.enclouds.enpoint.room.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoomCleanupScheduler {

    private final RoomService roomService;

    // 10분마다 실행
    @Scheduled(cron = "0 0 * * * *")
    public void cleanup() {
        int count = roomService.deleteOldRooms();
        if (count > 0) {
            System.out.println("삭제된 ROOM: " + count);
        }
    }
}