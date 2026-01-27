package com.enclouds.enpoint.room.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoomMapper {

    List<String> selectRoomCodes(@Param("gameSeq") Long gameSeq);

    String selectLatestRoomCode(@Param("gameSeq") Long gameSeq);

    int insertRoom(@Param("gameSeq") Long gameSeq,
                   @Param("roomCode") String roomCode);
}
