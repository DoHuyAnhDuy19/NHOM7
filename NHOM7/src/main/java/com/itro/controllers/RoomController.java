package com.itro.controllers;

import com.itro.dto.ApiResponse;
import com.itro.dto.RoomDTO;
import com.itro.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rooms")
@CrossOrigin(origins = "*", maxAge = 3600)
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<ApiResponse> createRoom(@RequestParam Long ownerId, @RequestBody RoomDTO roomDTO) {
        try {
            var room = roomService.createRoom(ownerId, roomDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Tạo phòng thành công", room));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Tạo phòng thất bại", e.getMessage()));
        }
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<ApiResponse> updateRoom(@PathVariable Long roomId, @RequestBody RoomDTO roomDTO) {
        try {
            var room = roomService.updateRoom(roomId, roomDTO);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật phòng thành công", room));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật phòng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<ApiResponse> getRoomById(@PathVariable Long roomId) {
        try {
            var room = roomService.getRoomById(roomId);
            return ResponseEntity.ok(ApiResponse.success("Lấy thông tin phòng thành công", room));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy thông tin phòng thất bại", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAllRooms() {
        try {
            var rooms = roomService.getAllRooms();
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách phòng thành công", rooms));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách phòng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<ApiResponse> getRoomsByOwner(@PathVariable Long ownerId) {
        try {
            var rooms = roomService.getRoomsByOwner(ownerId);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách phòng của chủ nhà thành công", rooms));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách phòng thất bại", e.getMessage()));
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<ApiResponse> getRoomsByStatus(@PathVariable String status) {
        try {
            var rooms = roomService.getRoomsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success("Lấy danh sách phòng theo trạng thái thành công", rooms));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Lấy danh sách phòng thất bại", e.getMessage()));
        }
    }

    @PatchMapping("/{roomId}/status")
    public ResponseEntity<ApiResponse> updateRoomStatus(@PathVariable Long roomId, @RequestParam String status) {
        try {
            roomService.updateRoomStatus(roomId, status);
            return ResponseEntity.ok(ApiResponse.success("Cập nhật trạng thái phòng thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Cập nhật trạng thái phòng thất bại", e.getMessage()));
        }
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<ApiResponse> deleteRoom(@PathVariable Long roomId) {
        try {
            roomService.deleteRoom(roomId);
            return ResponseEntity.ok(ApiResponse.success("Xóa phòng thành công"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Xóa phòng thất bại", e.getMessage()));
        }
    }
}
