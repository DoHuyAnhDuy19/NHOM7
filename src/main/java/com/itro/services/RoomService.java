// Thành viên 1 (Phạm Minh Duy : 06/05/2026) — Trang chính & Quản lý phòng
// Chức năng: Interface định nghĩa các phương thức xử lý logic nghiệp vụ liên quan đến phòng
package com.itro.services;

import com.itro.dto.RoomDTO;
import java.util.List;
import java.util.Map;

public interface RoomService {
    RoomDTO createRoom(Long ownerId, RoomDTO roomDTO);
    RoomDTO updateRoom(Long roomId, RoomDTO roomDTO);
    RoomDTO getRoomById(Long roomId);
    List<RoomDTO> getAllRooms();
    List<RoomDTO> getRoomsByOwner(Long ownerId);
    List<RoomDTO> getRoomsByStatus(String status);
    // Tìm kiếm phòng theo từ khóa mã phòng hoặc tên phòng
    List<RoomDTO> searchRooms(String keyword);
    // Lấy thống kê dashboard phòng
    Map<String, Long> getRoomSummary();
    // Kiểm tra xem phòng có đang trống hay không
    boolean isRoomAvailable(Long roomId);
    void deleteRoom(Long roomId);
    void updateRoomStatus(Long roomId, String status);
}
