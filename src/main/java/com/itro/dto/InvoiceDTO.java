// Thành viên 3 (Lê Hoàng Nam : 06/05/2026) — Dịch vụ & Hóa đơn
// Chức năng: DTO class để truyền dữ liệu hóa đơn giữa các tầng của ứng dụng
package com.itro.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDTO {
    private Long id;
    private String invoiceNumber;
    private Long contractId;
    private String contractNumber;
    private Long tenantId;
    private String tenantName;
    private LocalDate issuedDate;
    private LocalDate dueDate;
    private BigDecimal rentalAmount;
    private BigDecimal waterBill;
    private BigDecimal electricityBill;
    private BigDecimal otherCharges;
    private BigDecimal totalAmount;
    private BigDecimal paidAmount;
    private String status;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
