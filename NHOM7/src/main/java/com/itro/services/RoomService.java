package com.itro.services;

import com.itro.dto.RoomDTO;
import java.util.List;

public interface RoomService {
    RoomDTO createRoom(Long ownerId, RoomDTO roomDTO);
    RoomDTO updateRoom(Long roomId, RoomDTO roomDTO);
    RoomDTO getRoomById(Long roomId);
    List<RoomDTO> getAllRooms();
    List<RoomDTO> getRoomsByOwner(Long ownerId);
    List<RoomDTO> getRoomsByStatus(String status);
    void deleteRoom(Long roomId);
    void updateRoomStatus(Long roomId, String status);
}
