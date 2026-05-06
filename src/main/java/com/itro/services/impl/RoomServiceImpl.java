// Thành viên 1 (Phạm Minh Duy : 06/05/2026) — Trang chính & Quản lý phòng
// Chức năng: Implementation của RoomService, xử lý logic nghiệp vụ quản lý phòng
package com.itro.services.impl;

import com.itro.dto.RoomDTO;
import com.itro.models.Room;
import com.itro.models.User;
import com.itro.repositories.RoomRepository;
import com.itro.repositories.UserRepository;
import com.itro.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public RoomDTO createRoom(Long ownerId, RoomDTO roomDTO) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Chủ nhà không tồn tại"));

        if (roomRepository.findByRoomNumber(roomDTO.getRoomNumber()).isPresent()) {
            throw new RuntimeException("Số phòng đã tồn tại");
        }

        Room room = Room.builder()
                .roomNumber(roomDTO.getRoomNumber())
                .roomName(roomDTO.getRoomName())
                .description(roomDTO.getDescription())
                .area(roomDTO.getArea())
                .rentalPrice(roomDTO.getRentalPrice())
                .maxTenants(roomDTO.getMaxTenants())
                .status(Room.RoomStatus.AVAILABLE)
                .owner(owner)
                .imageUrl(roomDTO.getImageUrl())
                .isActive(true)
                .build();

        roomRepository.save(room);
        return convertToRoomDTO(room);
    }

    @Override
    public RoomDTO updateRoom(Long roomId, RoomDTO roomDTO) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));

        if (roomDTO.getRoomName() != null) room.setRoomName(roomDTO.getRoomName());
        if (roomDTO.getDescription() != null) room.setDescription(roomDTO.getDescription());
        if (roomDTO.getArea() != null) room.setArea(roomDTO.getArea());
        if (roomDTO.getRentalPrice() != null) room.setRentalPrice(roomDTO.getRentalPrice());
        if (roomDTO.getMaxTenants() != null) room.setMaxTenants(roomDTO.getMaxTenants());
        if (roomDTO.getImageUrl() != null) room.setImageUrl(roomDTO.getImageUrl());

        roomRepository.save(room);
        return convertToRoomDTO(room);
    }

    @Override
    public RoomDTO getRoomById(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        return convertToRoomDTO(room);
    }

    @Override
    public List<RoomDTO> getAllRooms() {
        return roomRepository.findByIsActive(true)
                .stream()
                .map(this::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomsByOwner(Long ownerId) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Chủ nhà không tồn tại"));
        return roomRepository.findByOwner(owner)
                .stream()
                .filter(Room::getIsActive)
                .map(this::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomDTO> getRoomsByStatus(String status) {
        return roomRepository.findByStatus(Room.RoomStatus.valueOf(status.toUpperCase()))
                .stream()
                .filter(Room::getIsActive)
                .map(this::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    // Tìm kiếm phòng theo mã phòng hoặc tên phòng
    @Override
    public List<RoomDTO> searchRooms(String keyword) {
        return roomRepository.findByRoomNumberContainingIgnoreCaseOrRoomNameContainingIgnoreCase(keyword, keyword)
                .stream()
                .filter(Room::getIsActive)
                .map(this::convertToRoomDTO)
                .collect(Collectors.toList());
    }

    // Lấy thống kê dashboard phòng: tổng phòng, phòng trống, phòng đã thuê, phòng bảo trì
    @Override
    public Map<String, Long> getRoomSummary() {
        long totalRooms = roomRepository.countByIsActive(true);
        long availableRooms = roomRepository.countByStatus(Room.RoomStatus.AVAILABLE);
        long rentedRooms = roomRepository.countByStatus(Room.RoomStatus.RENTED);
        long maintenanceRooms = roomRepository.countByStatus(Room.RoomStatus.MAINTENANCE);

        return Map.of(
                "totalRooms", totalRooms,
                "availableRooms", availableRooms,
                "rentedRooms", rentedRooms,
                "maintenanceRooms", maintenanceRooms
        );
    }

    // Kiểm tra phòng có đang trống (AVAILABLE) hay không
    @Override
    public boolean isRoomAvailable(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        return room.getStatus() == Room.RoomStatus.AVAILABLE && room.getIsActive();
    }

    @Override
    public void deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        room.setIsActive(false);
        roomRepository.save(room);
    }

    @Override
    public void updateRoomStatus(Long roomId, String status) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Phòng không tồn tại"));
        room.setStatus(Room.RoomStatus.valueOf(status.toUpperCase()));
        roomRepository.save(room);
    }

    private RoomDTO convertToRoomDTO(Room room) {
        return RoomDTO.builder()
                .id(room.getId())
                .roomNumber(room.getRoomNumber())
                .roomName(room.getRoomName())
                .description(room.getDescription())
                .area(room.getArea())
                .rentalPrice(room.getRentalPrice())
                .maxTenants(room.getMaxTenants())
                .status(room.getStatus().toString())
                .ownerId(room.getOwner().getId())
                .ownerName(room.getOwner().getFullName())
                .imageUrl(room.getImageUrl())
                .isActive(room.getIsActive())
                .createdAt(room.getCreatedAt())
                .updatedAt(room.getUpdatedAt())
                .build();
    }
}
