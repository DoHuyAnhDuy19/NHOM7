// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: DTO class để truyền dữ liệu dịch vụ giữa các tầng của ứng dụng
package com.itro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ServiceDTO {
    private Long id;
    private String serviceName;
    private String description;
    private BigDecimal price;
    private String serviceType;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
