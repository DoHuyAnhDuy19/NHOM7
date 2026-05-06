// Thành viên 4 (Vũ Tuấn Minh : 06/05/2026) — Hợp đồng, Khách thuê & Phòng
// Chức năng: DTO class để truyền dữ liệu khách thuê giữa các tầng của ứng dụng
package com.itro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TenantDTO {
    private Long id;
    private Long userId;
    private String userName;
    private String userEmail;
    private String userPhone;
    private String idNumber;
    private String idType;
    private String address;
    private String workplace;
    private String emergencyContact;
    private String emergencyPhone;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
