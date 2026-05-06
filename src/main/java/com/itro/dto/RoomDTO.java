// Thành viên 1 (Phạm Minh Duy : 06/05/2026) — Trang chính & Quản lý phòng
// Chức năng: DTO class để truyền dữ liệu phòng giữa các tầng của ứng dụng
package com.itro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomDTO {
    private Long id;
    private String roomNumber;
    private String roomName;
    private String description;
    private BigDecimal area;
    private BigDecimal rentalPrice;
    private Integer maxTenants;
    private String status;
    private Long ownerId;
    private String ownerName;
    private String imageUrl;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
