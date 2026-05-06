// Thành viên 1 (Phạm Minh Duy : 06/05/2026) — Trang chính & Quản lý phòng
// Chức năng: Repository interface cho entity Room, cung cấp các phương thức truy vấn dữ liệu phòng
package com.itro.repositories;

import com.itro.models.Room;
import com.itro.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByRoomNumber(String roomNumber);
    List<Room> findByOwner(User owner);
    List<Room> findByOwnerAndStatus(User owner, Room.RoomStatus status);
    List<Room> findByStatus(Room.RoomStatus status);
    List<Room> findByRoomNumberContainingIgnoreCaseOrRoomNameContainingIgnoreCase(String roomNumber, String roomName);
    long countByIsActive(Boolean isActive);
    long countByStatus(Room.RoomStatus status);
    List<Room> findByIsActive(Boolean isActive);
}
