// Thành viên 4 (Vũ Tuấn Minh : 06/05/2026) — Hợp đồng, Khách thuê & Phòng
// Chức năng: DTO class để truyền dữ liệu hợp đồng giữa các tầng của ứng dụng
package com.itro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContractDTO {
    private Long id;
    private String contractNumber;
    private Long roomId;
    private String roomName;
    private Long tenantId;
    private String tenantName;
    private Long ownerId;
    private String ownerName;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal rentalAmount;
    private BigDecimal depositAmount;
    private String terms;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
