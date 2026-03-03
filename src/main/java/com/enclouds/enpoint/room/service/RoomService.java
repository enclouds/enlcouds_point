package com.enclouds.enpoint.room.service;

import com.enclouds.enpoint.room.mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RoomService {

    @Autowired
    private RoomMapper roomMapper;

    // 룸 목록
    public List<String> getRoomCodes(Long gameSeq) {
        return roomMapper.selectRoomCodes(gameSeq);
    }

    public String createOrGetRoom(Long gameSeq) {
        for (int i = 0; i < 3; i++) {
            String code = genRoomCode();
            try {
                roomMapper.insertRoom(gameSeq, code);
                return code;
            } catch (DuplicateKeyException e) {
                // 충돌 시 재시도
            }
        }
        throw new IllegalStateException("ROOM_CODE 생성 실패");
    }

    private String genRoomCode() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 8).toUpperCase();
    }

    public int deleteOldRooms() {
        return roomMapper.deleteOldRooms();
    }

}
